package AesUtils

import (
	"bytes"
	"crypto/aes"
	"crypto/cipher"
	"crypto/rand"
	"encoding/base64"
	"io"
)

func pKCS5Padding(ciphertext []byte, blockSize int) []byte {
	padding := blockSize - len(ciphertext)%blockSize
	padText := bytes.Repeat([]byte{byte(padding)}, padding)
	return append(ciphertext, padText...)
}

func pKCS5UnPadding(origData []byte) []byte {
	length := len(origData)
	unPadding := int(origData[length-1])
	return origData[:(length - unPadding)]
}

// AesCBCEncrypt aes加密，填充秘钥key的16位，24,32分别对应AES-128, AES-192, or AES-256.
func AesCBCEncrypt(rawData, key, iv []byte) ([]byte, error) {
	block, err := aes.NewCipher(key)
	if err != nil {
		panic(err)
	}
	//填充原文
	blockSize := block.BlockSize()
	rawData = pKCS5Padding(rawData, blockSize)
	//初始向量IV必须是唯一，但不需要保密
	cipherText := make([]byte, len(rawData))
	//block大小 16
	//block大小和初始向量大小一定要一致
	mode := cipher.NewCBCEncrypter(block, iv)
	mode.CryptBlocks(cipherText, rawData)
	return cipherText, nil
}

func CBCDecrypt(encryptData, key, iv []byte) ([]byte, error) {
	block, err := aes.NewCipher(key)
	if err != nil {
		panic(err)
	}
	blockSize := block.BlockSize()

	if len(encryptData) < blockSize {
		panic("ciphertext too short")
	}

	// CBC mode always works in whole blocks.
	if len(encryptData)%blockSize != 0 {
		panic("ciphertext is not a multiple of the block size")
	}

	mode := cipher.NewCBCDecrypter(block, iv)

	// CryptBlocks can work in-place if the two arguments are the same.
	mode.CryptBlocks(encryptData, encryptData)
	//解填充
	encryptData = pKCS5UnPadding(encryptData)
	return encryptData, nil
}

func DiyEncrypt(rawData string, key []byte) (string, error) {
	iv := make([]byte, 16)
	if _, err := io.ReadFull(rand.Reader, iv); err != nil {
		panic(err)
	}
	data, err := AesCBCEncrypt([]byte(rawData), key, iv)
	if err != nil {
		return "", err
	}
	reData := make([]byte, 16+len(data))
	copy(reData[:16], iv)
	copy(reData[16:], data)
	return base64.RawStdEncoding.EncodeToString(reData), nil
}

func DiyDecrypt(rawData string, key []byte) (string, error) {
	data, err := base64.RawStdEncoding.DecodeString(rawData)
	if err != nil {
		return "", err
	}
	dnData, err := CBCDecrypt(data[16:], key, data[:16])
	if err != nil {
		return "", err
	}
	return string(dnData), nil
}
