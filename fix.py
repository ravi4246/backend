import os
import glob
import re

directory = r"c:\Users\raviv\AndroidStudioProjects\MyApplication7\app\src\main\java\com\simats\siddha\myapplication"
files = glob.glob(os.path.join(directory, "*.kt"))

for file in files:
    with open(file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    original_content = content
    modified = False
    
    if "override suspend fun login(" in content and "refreshToken" not in content:
        # Match single-line override
        content = re.sub(
            r"(override suspend fun login\(credentials: com\.simats\.siddha\.myapplication\.api\.LoginRequest\) = .*?TokenResponse\(\"\", \"\"\)\))\s*\n",
            r"\1\n            override suspend fun refreshToken(request: com.simats.siddha.myapplication.api.RefreshTokenRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.TokenResponse(\"\", \"\"))\n",
            content
        )
        
        # Match multi-line override like in DailyLogScreen
        if "override suspend fun login(credentials: LoginRequest): Response<TokenResponse> {" in content:
            content = re.sub(
                r"(override suspend fun login\(credentials: LoginRequest\): Response<TokenResponse> \{\s*return Response\.success\(TokenResponse\(\"\", \"\"\)\)\s*\})",
                r"\1\n\n            override suspend fun refreshToken(request: RefreshTokenRequest): Response<TokenResponse> {\n                return Response.success(TokenResponse(\"\", \"\"))\n            }",
                content
            )

    # Replace UserViewModel instantiations that don't have AuthManager yet
    content = content.replace(
        "UserViewModel(Repository(dummyApiService))", 
        "UserViewModel(Repository(dummyApiService), com.simats.siddha.myapplication.api.AuthManager(androidx.compose.ui.platform.LocalContext.current))"
    )
    content = content.replace(
        "UserViewModel(com.simats.siddha.myapplication.api.Repository(dummyApiService))", 
        "UserViewModel(com.simats.siddha.myapplication.api.Repository(dummyApiService), com.simats.siddha.myapplication.api.AuthManager(androidx.compose.ui.platform.LocalContext.current))"
    )

    if content != original_content:
        with open(file, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Fixed {os.path.basename(file)}")
