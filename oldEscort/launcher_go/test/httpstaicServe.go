package main

import (
	"crypto/md5"
	"fmt"
	"io"
	"net"
	"os"
)

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
		fmt.Println(mac)
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

func main1() {
	machineID, err := getMachineID()
	if err != nil {
		fmt.Println("无法生成机器码:", err)
	} else {
		fmt.Println("机器码:", machineID)
	}
}
