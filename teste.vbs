Set sh = CreateObject("WScript.Shell")

cmd = """C:\Program Files\Java\jdk-26.0.1\bin\java.exe""" & _
      " --module-path ""C:\Users\JM-Tecnologias\Documents\NetBeansProjects\JMVIS\JM Vision Intelligence System\dist\lib\javafx-sdk-17.0\lib""" & _
      " --add-modules javafx.controls,javafx.fxml" & _
      " -cp ""C:\Users\JM-Tecnologias\Documents\NetBeansProjects\JMVIS\JM Vision Intelligence System\dist\JMVisionIntelligenceSystem.jar;C:\Users\JM-Tecnologias\Documents\NetBeansProjects\JMVIS\JM Vision Intelligence System\dist\lib\*""" & _
      " jmvis.Main"

sh.Run cmd, 0, False