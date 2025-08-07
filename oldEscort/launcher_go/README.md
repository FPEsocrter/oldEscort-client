# launcher_go

go get github.com/josephspurrier/goversioninfo/cmd/goversioninfo


go generate


go build -o start-software.exe -ldflags="-H windowsgui -w -s"
