package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
)

func handler(w http.ResponseWriter, r *http.Request) {
	// 打印请求方法
	fmt.Println("Method:", r.Method)

	// 打印请求URL
	fmt.Println("URL:", r.URL)

	// 打印请求头
	fmt.Println("Headers:")
	for key, values := range r.Header {
		for _, value := range values {
			fmt.Printf("  %s: %s\n", key, value)
		}
	}

	// 读取请求体并打印
	body, err := ioutil.ReadAll(r.Body)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	defer r.Body.Close()
	fmt.Println("Body:", string(body))

	fmt.Fprint(w, "Request details printed to the console. Check your server logs.")
}

func main() {
	http.HandleFunc("/", handler)
	http.ListenAndServe("127.0.0.1:8080", nil)
}
