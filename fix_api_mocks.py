import os
import glob

app_dir = r"c:\Users\raviv\AndroidStudioProjects\MyApplication7\app\src\main\java\com\simats\siddha\myapplication"

chat_method = """
            override suspend fun chat(request: com.simats.siddha.myapplication.api.ChatRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.ChatResponse(""))
"""

for filepath in glob.glob(os.path.join(app_dir, "*.kt")):
    with open(filepath, "r", encoding="utf-8") as f:
        content = f.read()
    
    # Check if this file has the dummy ApiService
    if "val dummyApiService = object :" in content and "fun chat(" not in content:
        # Find where to insert it. Usually after resetPassword.
        target_str = 'override suspend fun resetPassword(request: com.simats.siddha.myapplication.api.ResetPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))'
        
        if target_str in content:
            new_content = content.replace(target_str, target_str + chat_method)
            with open(filepath, "w", encoding="utf-8") as f:
                f.write(new_content)
            print(f"Patched {os.path.basename(filepath)}")
        else:
            print(f"Target string not found in {os.path.basename(filepath)}")
