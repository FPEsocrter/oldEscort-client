package online

import (
	"crypto/md5"
	"fmt"
	"io"
	"launcher_go/Utils/crypto/AesUtils"
	"net"
	"net/http"
	"os"
	"strconv"
	"time"
)

var (
	onLineAesKey = []byte("1c03e6d1fc4a88b7010c6fecc9698251")
)

func timer() {
	go func() {
		interval := 10 * time.Minute
		ticker := time.NewTicker(interval)

		for {
			select {
			case <-ticker.C:
				// 在这里执行你的操作
				line()
			}
		}
	}()
}

func line() {

	currentTime := time.Now()
	timestamp := currentTime.Unix()
	strTimestamp, _ := AesUtils.DiyEncrypt(strconv.FormatInt(timestamp, 10), onLineAesKey)
	formatted := fmt.Sprintf(`http://127.0.0.1:%d/showUi/%s`, info.StaticPort, strTimestamp)
	_, _ = http.Get(formatted)
}

func getMachineID() (string, error) {
	hostname, err := os.Hostname()
	if err != nil {
		return "", err
	}

	// 获取MAC地址
	interfaces, err := net.Interfaces()
	if err != nil {
		return "", err
	}
	var macAddresses []string
	for _, inter := range interfaces {
		macAddresses = append(macAddresses, inter.HardwareAddr.String())
	}

	// 将主机名和MAC地址连接在一起
	machineID := hostname
	fmt.Println(hostname)
	for _, mac := range macAddresses {
		machineID += mac
	}

	// 计算MD5散列值以生成唯一机器码
	h := md5.New()
	_, err = io.WriteString(h, machineID)
	if err != nil {
		return "", err
	}

	return fmt.Sprintf("%x", h.Sum(nil)), nil
}
