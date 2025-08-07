package ui

import (
	"bufio"
	"fmt"
	"launcher_go/business/share"
	"launcher_go/gow32"
	"os"
	"os/exec"
	"path/filepath"
	"regexp"
	"strconv"
	"strings"
	"syscall"
	"time"
)

var (
	chromiumPath     = "\\env\\browser\\chrome.exe"
	chromiumMapAppId = map[uint16]string{
		80:    "dlbmfdiobcnhnfocmenonncepnmhpckd",
		8080:  "obcppbejhdfcplncjdlmagmpfjhmipii",
		8081:  "iopdpcnaonaidlnhpbgliikiofpkdblo",
		8082:  "cibpboibpedkaagmppnjeingegpoladf",
		8083:  "lcblfgnemppnlafpdnhjkfcdbkpbbgke",
		8089:  "adhjjcdabenghlegjafajnahnbokkaoc",
		45623: "jdpppgnbgldcaamoepiobhcllfoeknkl",
		35681: "mokgcccfipokacalokobomggiclkeklo",
		61231: "pkcapfafkbijakkecfdjegaegflfgfao",
		56971: "bflpdlgpcjbajdeogaajchifmoaoolee",
	}
	profileDirectory = "Default"
	userDataDir      = "\\env\\User Data"

	chromiumAppIdCmd       = "--app-id="
	profileDirectoryCmd    = "--profile-directory="
	userDataDirCmd         = "--user-data-dir="
	remoteDebuggingPortCmd = "--remote-debugging-port=0"

	title = "护航游览器" //todo
	class = "Chrome_WidgetWin_1"
)
var (
	alive  = false
	cmd    *exec.Cmd //p
	handle syscall.Handle
	wsPort uint16
)

func Run(staticPort uint16) uint16 {

	exePath, _ := os.Executable()
	exeDir := filepath.Dir(exePath)

	chromeCommand := []string{
		chromiumAppIdCmd + chromiumMapAppId[staticPort],
		remoteDebuggingPortCmd,
		profileDirectoryCmd + profileDirectory,
		userDataDirCmd + exeDir + userDataDir,
	}
	//fmt.Println(chromeCommand)
	cmd = exec.Command(exeDir+chromiumPath, chromeCommand...)
	cmd.Env = append(os.Environ(),
		"GOOGLE_API_KEY=no",
		"GOOGLE_DEFAULT_CLIENT_ID=no",
		"GOOGLE_DEFAULT_CLIENT_SECRET=no",
	)
	stderr, err := cmd.StderrPipe()
	if err != nil {
		return 0
	}
	err = cmd.Start()
	if err != nil {
		fmt.Println("启动失败:", err)
		return 0
	}
	fmt.Println(cmd.Process.Pid)
	alive = true
	go func() {
		scanner := bufio.NewScanner(stderr)
		for scanner.Scan() {
			line := scanner.Text()
			if strings.Contains(line, "ws://") {
				pattern := `ws://127.0.0.1:(\d+)/devtools/browser/`
				// 编译正则表达式
				re := regexp.MustCompile(pattern)
				// 查找匹配项
				matches := re.FindStringSubmatch(line)
				// 如果找到匹配项
				if len(matches) >= 2 {
					num, _ := strconv.Atoi(matches[1])
					wsPort = uint16(num)
					fmt.Println(wsPort)
					break
				}

			}
		}

	}()

	go func() {
		_ = cmd.Wait()
		_ = cmd.Process.Kill()
		alive = false
		wsPort = 0
	}()
	go func() {
		for {
			time.Sleep(500)
			windowHandle, err := gow32.FindWindow(title, class)
			if err != nil {
				continue
			}
			handle = windowHandle
		}
	}()
	return 0
}

func Kill() {
	if cmd != nil && alive {
		_ = cmd.Process.Kill()
	}

}

func isAlive() bool {
	return alive
}

func Show() {
	if isAlive() {
		_, _ = gow32.ShowWindow(handle)
		_, _ = gow32.RestoreWindow(handle)
		width, height, _ := gow32.GetSystemMetrics()
		_, _ = gow32.SetWindowSizAndTop(handle, int(float64(width)*0.1), int(float64(height)*0.1), int(float64(width)*0.8), int(float64(height)*0.8))

		//显示 取消最小化 置顶
	} else {
		read := share.Read()
		fmt.Println("share.Read", read)
		if read.StaticPort != 0 {
			Run(read.StaticPort)
		}

	}
}

func Hide() {
	_, _ = gow32.HideWindow(handle)
}
