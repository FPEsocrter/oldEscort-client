package businessSystem

import (
	"fmt"
	"launcher_go/Utils/crypto/AesUtils"
	"launcher_go/Utils/netPort"
	"os"
	"os/exec"
	"path/filepath"
	"strconv"
	"time"
)

var (
	workPath             = "\\bin"
	businessSystemPath   = workPath + "\\launcher.dll"
	portCmd              = "--server.port="
	timestampCmd         = "--timestamp"
	businessSystemAesKey = []byte("b04d265ac800a83c3f1587ea0ae15411")
)

var (
	cmd   *exec.Cmd
	alive = false
)

func Run() uint16 {
	var chromeCommand []string
	port, portStr := netPort.AvailablePort([]uint16{})
	currentTime := time.Now()
	timestamp := currentTime.Unix()

	exePath, _ := os.Executable()
	exeDir := filepath.Dir(exePath)
	exe := exeDir + businessSystemPath

	/* //显示窗口
	chromeCommand = append(chromeCommand, "/C")
	chromeCommand = append(chromeCommand, "start")
	chromeCommand = append(chromeCommand, exe)
	exe = "cmd.exe"*/

	encrypt, _ := AesUtils.DiyEncrypt(portCmd+portStr, businessSystemAesKey)
	chromeCommand = append(chromeCommand, encrypt)

	timestampStr, _ := AesUtils.DiyEncrypt(timestampCmd+strconv.FormatInt(timestamp, 10), businessSystemAesKey)
	chromeCommand = append(chromeCommand, timestampStr)
	fmt.Print(exe, chromeCommand)
	cmd = exec.Command(exe, chromeCommand...)
	// 设置工作目录
	cmd.Dir = exeDir + workPath

	// 设置标准输入、输出和错误输出
	err := cmd.Start()
	if err != nil {
		fmt.Println("启动失败:", err)
		return 0
	}
	fmt.Println(cmd.Process.Pid)
	alive = true
	go func() {
		_, _ = cmd.Process.Wait()
		_ = cmd.Process.Kill()
		alive = false
		cmd = nil
	}()
	return port
}

func Kill() {
	if cmd != nil && cmd.Process != nil && IsAlive() {
		_ = cmd.Process.Kill()
	}
}

func IsAlive() bool {
	return alive
}
