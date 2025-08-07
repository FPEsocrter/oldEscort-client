package rpc

import (
	"fmt"
	"launcher_go/Utils/crypto/AesUtils"
	"launcher_go/business/share"
	"net/http"
	"strconv"
	"time"
)

var (
	showUIAesKey = []byte("1c03e6d1fc4a88b7010c6fecc9698251")
)

// OpenEvn todo
func OpenEvn(id string, info share.Info) {

}
func ShowUI(info share.Info) {
	currentTime := time.Now()
	timestamp := currentTime.Unix()
	strTimestamp, _ := AesUtils.DiyEncrypt(strconv.FormatInt(timestamp, 10), showUIAesKey)
	formatted := fmt.Sprintf(`http://127.0.0.1:%d/showUi/%s`, info.StaticPort, strTimestamp)
	_, _ = http.Get(formatted)
}
