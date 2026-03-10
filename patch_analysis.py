import os, re
path = r'c:\Users\raviv\AndroidStudioProjects\MyApplication7\app\src\main\java\com\simats\siddha\myapplication'

for root, _, files in os.walk(path):
    for str_file in files:
        if str_file.endswith('.kt') and str_file != 'AnalysisEntity.kt' and str_file != 'Models.kt':
            fpath = os.path.join(root, str_file)
            with open(fpath, 'r', encoding='utf-8') as f:
                content = f.read()

            new_content = re.sub(
                r'AnalysisResponse\(\s*\"Low Risk\",\s*\"Improving\",\s*82,\s*85,\s*listOf\(0\.9f,\s*0\.82f\),\s*emptyList\(\),\s*\"Keep up good habits\",\s*emptyList\(\)\)',
                'AnalysisResponse(\"Low Risk\", \"Improving\", 82, 85, listOf(0.9f, 0.82f), emptyList(), \"Keep up good habits\", null, emptyList())',
                content
            )
            
            new_content = re.sub(
                r'AnalysisResponse\(riskLevel\s*=\s*\"Low Risk\",\s*trend\s*=\s*\"Improving\",\s*adherenceLevel\s*=\s*82,\s*confidenceScore\s*=\s*80,\s*trendGraph\s*=\s*listOf\(0\.9f,\s*0\.82f\),\s*riskFactors\s*=\s*emptyList\(\),\s*recommendation\s*=\s*\"Keep it up\",\s*insights\s*=\s*emptyList\(\)\)',
                'AnalysisResponse(riskLevel = \"Low Risk\", trend = \"Improving\", adherenceLevel = 82, confidenceScore = 80, trendGraph = listOf(0.9f, 0.82f), riskFactors = emptyList(), recommendation = \"Keep it up\", recommendedPlan = null, insights = emptyList())',
                new_content
            )
            
            if new_content != content:
                with open(fpath, 'w', encoding='utf-8') as f:
                    f.write(new_content)
                print(f'Fixed {str_file}')
