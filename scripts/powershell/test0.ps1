#读取文件内容
$content = Get-Content -Path "./token.txt"
# 输出文件内容到控制台
echo "The content of token.txt is: $content"
# 设置环境变量名为 MY_Laker_ENV
$env:MY_Laker_ENV = $content
# 输出环境变量到控制台
Write-Output "The value of MY_ENV_VAR is: $env:MY_Laker_ENV"