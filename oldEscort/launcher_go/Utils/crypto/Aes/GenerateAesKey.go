package main

import (
	"crypto/rand"
	"fmt"
	"io"
)

func generateAESKey(keySize int) ([]byte, error) {
	key := make([]byte, keySize)
	_, err := io.ReadFull(rand.Reader, key)
	if err != nil {
		return nil, err
	}
	return key, nil
}

func main() {
	// 指定 AES 密钥长度，可以是 16、24、或 32 字节（128、192、或 256 位）
	keySize := 16 // 使用 128 位密钥

	// 生成 AES 密钥
	aesKey, err := generateAESKey(keySize)
	if err != nil {
		fmt.Println("密钥生成失败:", err)
		return
	}

	// 打印生成的密钥（密钥是随机的）
	fmt.Printf("生成的 AES 密钥: %x\n", aesKey)
}
