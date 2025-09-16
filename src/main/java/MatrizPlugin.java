// src/main/java/br/seulab/MatrizPlugin.java

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;

import java.io.File;
import java.util.Arrays;

public class MatrizPlugin implements PlugIn {

	@Override
	public void run(String arg) {
		// Escolhe pasta (equiv. getDirectory("Selecione uma pasta"))
		String directory = IJ.getDirectory("Selecione uma pasta");
		if (directory == null) return;

		// Cria subpasta "matriz" (equiv. File.makeDirectory(matrizDir))
		String matrizDir = directory + (directory.endsWith(File.separator) ? "" : File.separator) + "matriz" + File.separator;
		new File(matrizDir).mkdirs();

		// Lista arquivos
		File dir = new File(directory);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			IJ.error("Nenhum arquivo encontrado na pasta selecionada.");
			return;
		}

		// Processa todos os arquivos não-diretório
		Arrays.sort(files);
		for (File f : files) {
			if (f == null || !f.isFile()) continue;

			// Abre imagem (equiv. open(replace(directory+fileList[i], "\\", "/")))
			ImagePlus imp = IJ.openImage(f.getAbsolutePath());
			if (imp == null) continue;

			try {
				processOne(imp, f.getName(), matrizDir);
			} catch (Throwable t) {
				IJ.log("Falha ao processar " + f.getName() + ": " + t.getMessage());
			} finally {
				// Fecha a imagem original (equiv. close() após process())
				if (imp != null) imp.close();
			}
		}

		IJ.showStatus("Concluído!");
		IJ.run("Results...", ""); // só para garantir que a janela apareça
	}

	private void processOne(ImagePlus imp, String fileName, String matrizDir) {
		// ===== Macro original (porta 1:1) =====
		// run("Set Scale...", "distance=398 known=2 pixel=1 unit=mm");
		IJ.run(imp, "Set Scale...", "distance=398 known=2 pixel=1 unit=mm");

		// run("8-bit");
		IJ.run(imp, "8-bit", "");

		// run("Enhance Contrast...", "saturated=0.4 normalize equalize");
		IJ.run(imp, "Enhance Contrast...", "saturated=0.4 normalize equalize");

		// run("Set Measurements...", "add display area mean min perimeter area_fraction limit redirect=None decimal=3");
		IJ.run(imp, "Set Measurements...", "add display area mean min perimeter area_fraction limit redirect=None decimal=3");

		// run("Duplicate...", "title=" + novoNome);  // novoNome = "matriz-" + nome
		String novoNome = "matriz-" + fileName;
		IJ.run(imp, "Duplicate...", "title=" + novoNome);

		// selectWindow("matriz-" + nome);
		ImagePlus dup = IJ.getImage(); // a janela duplicada fica ativa

		// setThreshold(9, 147);
		dup.getProcessor().setThreshold(9, 147, 1);

		// setOption("BlackBackground", false);
		IJ.run("Options...", "iterations=1 count=1 black"); // garante estado; abaixo reforçamos
		IJ.run(dup, "Make Binary", ""); // "Convert to Mask" no macro -> equivalente Make Binary
		// Em IJ1, "Convert to Mask" e "Make Binary" têm equivalência funcional no pipeline
		// Para aderência máxima ao macro, poderíamos:
		// IJ.run(dup, "Convert to Mask", "");  // descomente se preferir exatamente este comando
		// (manterei Make Binary por robustez em diversos builds)

		// "Measure"
		IJ.run(dup, "Measure", "");

		// saveAs("jpeg", matrizDir + "matriz-" + nome);
		// Mantemos o mesmo comportamento do macro: salva como JPEG preservando o nome original após o prefixo.
		String outPath = matrizDir + "matriz-" + fileName; // pode terminar em .tif/.png, mas formato será JPEG
		IJ.saveAs(dup, "Jpeg", outPath);

		// close();
		dup.close();
		// ===== Fim da porta =====
	}
}
