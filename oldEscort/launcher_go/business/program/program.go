package program

import (
	"launcher_go/Utils/crypto/AesUtils"
	"launcher_go/gow32"
	"os"
	"strings"
)

var argAesCryptoKey = []byte("4241b98943e04ed7253e19e66df40704") //加密钥匙

var mutexName = "Global\\{73590C33-F35F-430A-8B70-FCB2E69C112A}"

func IsOtherRun() bool {

	mutexId, err := gow32.CreateMutex(mutexName)
	defer gow32.ReleaseMutex(mutexId)
	if err != nil {
		return true
	} else {
		return false
	}

}

func GetArgs() map[string]string {
	argsStr := os.Args
	argMap := make(map[string]string)

	if len(argsStr) == 2 && !strings.Contains(argsStr[1], "=") {
		argMap["id"] = argsStr[1]
	}
	if len(argsStr) > 2 {
		for i := 2; i < len(argsStr); i++ {
			arg := argsStr[i]
			if strings.Contains(arg, "=") {
				parts := strings.SplitN(arg, "=", 2) // 使用等号拆分参数
				key := strings.ToLower(parts[0])
				value := parts[1]
				argMap[key] = value
			}
		}
	}

	for key, value := range argMap {
		if decrypt, _ := AesUtils.DiyEncrypt(value, argAesCryptoKey); decrypt != "" {
			argMap[key] = decrypt
		}
	}

	return argMap
}
