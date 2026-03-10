import os
import glob

directory = r'C:\Users\raviv\AndroidStudioProjects\MyApplication7\app\src\main\java\com\simats\siddha\myapplication'
files = glob.glob(os.path.join(directory, '*.kt'))

methods_to_insert = """
            override suspend fun forgotPassword(request: com.simats.siddha.myapplication.api.ForgotPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))
            override suspend fun resetPassword(request: com.simats.siddha.myapplication.api.ResetPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))
"""

for filepath in files:
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Check if we need to patch
    if 'object : ApiService' in content or 'object : com.simats.siddha.myapplication.api.ApiService' in content:
        if 'forgotPassword(request' not in content:
            # Look for the last method in the mock, getAnalysis
            
            # Simple heuristic, replace the "        }" immediately following "            )"
            # Let's do a regex replacement to find the end of the fake object:
            import re
            
            # Find all occurrences of retrofit2.Response.success( ... )
            # The closing parenthesis is followed by newline and then "        }"
            
            # Better way: Search for "override suspend fun getAnalysis" block
            idx = content.find("override suspend fun getAnalysis")
            if idx != -1:
                # Find the next } matching the object block
                end_idx = content.find("        }", idx)
                if end_idx != -1:
                    new_content = content[:end_idx] + methods_to_insert + content[end_idx:]
                    with open(filepath, 'w', encoding='utf-8') as f:
                        f.write(new_content)
                    print(f"Patched {os.path.basename(filepath)}")
                else:
                    print(f"Could not find closing brace for {os.path.basename(filepath)}")
            else:
                print(f"No getAnalysis in {os.path.basename(filepath)}")
