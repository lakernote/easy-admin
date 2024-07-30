# ---【1.读取文件内容并设置为环境变量】---
#读取文件内容
$content = Get-Content -Path "./token.txt"
# echo 输出文件内容到控制台
Write-Output  "The content of token.txt is: $content"
# 设置环境变量名为 MY_Laker_ENV
$env:MY_Laker_ENV = $content # 设置环境变量
# 输出环境变量到控制台
Write-Output "The value of MY_ENV_VAR is: $env:MY_Laker_ENV"

# ---【2.与用户交互，获取用户输入内容】---
# 提示用户输入一些内容
$name = Read-Host "please input your name"

# 输出用户输入的内容
Write-Output "$name, welcome to Laker12!"

# ---【3.删除指定文件夹】---
# 获取脚本所在的目录
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
# 获取上级目录
$parentDir = Split-Path -Parent $scriptDir
# 输出上级目录
Write-Output "The parent directory is: $parentDir"
# 获取再上级目录
$grandParentDir = Split-Path -Parent $parentDir
# 输出再上级目录
Write-Output "The grandparent directory is: $grandParentDir"
# 输出脚本所在的目录
Write-Output "The script is in the directory: $scriptDir"

# 定义要删除的文件夹路径列表
$folders = @( # 数组
    "../../logs", # 每个元素是一个文件夹路径，可以是相对路径，也可以是绝对路径，相对脚本的路径。
    "$grandParentDir/target",
    "C:\laker\logs"
)

# 遍历每个文件夹路径并删除
foreach ($folder in $folders) # 遍历数组
{
    if (Test-Path -Path $folder) # 检查文件夹是否存在
    {
        Remove-Item -Path $folder -Recurse -Force # 删除文件夹
        Write-Host "deleted $folder" # 输出信息
    }
    else
    {
        Write-Host "not found $folder" # 输出信息
    }
}