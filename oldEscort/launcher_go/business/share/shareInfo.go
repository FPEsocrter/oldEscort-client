package share

import (
	"encoding/json"
	"fmt"
	"launcher_go/Utils/crypto/AesUtils"
	"launcher_go/gow32"
	"os"
	"os/user"
	"path/filepath"
	"time"
)

var (
	filePath         = "fpBowers.lock"
	fileAesCryptoKey = []byte("55b6577a72418ecaad7234dfbd2f2fa2")
	files            []*os.File
	shareInfo        *Info
)

type Info struct {
	WebPort    uint16
	StaticPort uint16
}

func create() {
	go func() {}()
	for _, path := range getPath() {
		file, err := os.OpenFile(path, os.O_CREATE|os.O_WRONLY, 0644)
		if err != nil {
			fmt.Println("创建文件异常", err)
			continue
		}
		files = append(files, file)
		for i := 0; i < 5; i++ {
			lockFile, _ := gow32.LockFile(file)
			if lockFile {
				break
			}
			time.Sleep(200 * time.Millisecond)
		}
	}

}

// Write 请勿频繁调用
func Write(info Info) {
	shareInfo = &info
	jsonData, err := json.Marshal(info)
	if err != nil {
		fmt.Println("JSON编码错误:", err)
		return // 处理编码错误
	}
	if files == nil {
		create()
	}
	encrypt, _ := AesUtils.DiyEncrypt(string(jsonData), fileAesCryptoKey)
	for _, file := range files {
		_ = file.Truncate(0)
		_, _ = file.Seek(0, 0) // 重新定位文件指针到文件开头
		_, _ = file.Write([]byte(encrypt))
		if err != nil {
			fmt.Println("文件写入错误:", err)
			continue // 处理文件写入错误
		}
	}

}

func Read() Info {
	return *shareInfo
}
func OneRead() Info {
	for _, path := range getPath() {
		data, err := os.ReadFile(path)
		if err != nil {
			continue
		}
		decrypt, _ := AesUtils.DiyDecrypt(string(data), fileAesCryptoKey)
		var info Info
		err = json.Unmarshal([]byte(decrypt), &info)
		if err != nil {
			fmt.Println("JSON解析错误:", err)
			continue // 处理解析错误
		}
		return info
	}
	return Info{0, 0}
}

func getPath() []string {
	var paths []string
	currentUser, err := user.Current()
	if err == nil {
		homeDir := currentUser.HomeDir
		paths = append(paths, homeDir+"\\"+filePath)
	}
	exePath, _ := os.Executable()
	exeDir := filepath.Dir(exePath)
	paths = append(paths, exeDir+"\\bin\\"+filePath)
	fmt.Println("paths:", paths)
	return paths
}

func Delete() {
	if files != nil {
		for _, file := range files {
			_ = file.Close()

		}
	}
	for _, path := range getPath() {
		err := os.Remove(path)
		if err != nil {
			fmt.Println("无法删除文件:", err)
		}
	}

}
