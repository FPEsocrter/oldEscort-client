package staticServe

import (
	"fmt"
	"launcher_go/Utils/crypto/AesUtils"
	"launcher_go/Utils/netPort"
	"launcher_go/business/ui"
	"net/http"
	"os"
	"path/filepath"
	"strconv"
	"strings"
	"time"
)

var (
	server       *http.Server
	showUIAesKey = []byte("1c03e6d1fc4a88b7010c6fecc9698251")
)

func GetServer() *http.Server {
	return server

}

func Run(webPort uint16) uint16 {
	port, portStr := netPort.AvailablePort([]uint16{80, 8080, 8081, 8082, 8083, 8089, 45623, 35681, 61231, 56971})
	// 创建文件服务器

	formatted := fmt.Sprintf(`{"PRODUCTION_URL":"http://127.0.0.1:%d"}`, webPort)
	fmt.Println(formatted)

	mux := http.NewServeMux()
	mux.Handle("/webapi.json", http.HandlerFunc(func(responseWriter http.ResponseWriter, r *http.Request) {

		_, _ = responseWriter.Write([]byte(formatted))

	}))
	mux.HandleFunc("/showUi/", func(responseWriter http.ResponseWriter, request *http.Request) {
		path := request.URL.Path
		newString := strings.Replace(path, "/showUi/", "", -1)
		decrypt, _ := AesUtils.DiyDecrypt(newString, showUIAesKey)
		intValue, err := strconv.Atoi(decrypt)
		if err != nil {
			fmt.Println("转换失败:", err)
			return
		}
		currentTime := time.Now()
		timestamp := currentTime.Unix()
		if timestamp-int64(intValue) < 15000 {
			ui.Show()
		}

	})

	noCache := func(h http.Handler) http.HandlerFunc {
		return func(w http.ResponseWriter, r *http.Request) {
			// 设置禁止缓存的响应头
			w.Header().Set("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0")
			w.Header().Set("Pragma", "no-cache")
			w.Header().Set("Expires", "Thu, 01 Jan 1970 00:00:00 GMT")
			h.ServeHTTP(w, r)
		}
	}
	exePath, _ := os.Executable()
	exeDir := filepath.Dir(exePath)
	// 创建文件服务器
	fs := http.FileServer(http.Dir(exeDir + "/resource"))
	mux.HandleFunc("/", func(responseWriter http.ResponseWriter, request *http.Request) {
		fmt.Println(request.URL.Path)
		if request.URL.Path == "/" || request.URL.Path == "/index.html" {
			noCache(fs).ServeHTTP(responseWriter, request)
		} else {
			fs.ServeHTTP(responseWriter, request)
		}
	})

	server = &http.Server{
		Addr:    "127.0.0.1:" + portStr,
		Handler: mux,
	}
	fmt.Println("启动HTTP服务器并指定监听地址和端口", portStr)
	go func() {
		// 启动HTTP服务器并指定监听地址和端口
		_ = server.ListenAndServe()
	}()

	return port
}
