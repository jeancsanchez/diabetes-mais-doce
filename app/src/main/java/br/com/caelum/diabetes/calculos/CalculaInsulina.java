package br.com.caelum.diabetes.calculos;

import br.com.caelum.diabetes.extras.TipoRefeicao;
import br.com.caelum.diabetes.model.DadosMedicos;
import br.com.caelum.diabetes.model.Glicemia;
import br.com.caelum.diabetes.model.Paciente;
import br.com.caelum.diabetes.model.Refeicao;

public class CalculaInsulina {
	Paciente paciente;
	private Refeicao refeicao;
	
	public CalculaInsulina(Refeicao refeicao, Paciente paciente) {
		this.refeicao = refeicao;
		this.paciente = paciente;
	}
	
	public double getTotalInsulina() {
		double totalCHO = refeicao.getTotalCHO();
		DadosMedicos correcao = paciente.getInsulinaCorrecao();
		TipoRefeicao tipoRefeicao = refeicao.getTipoRefeicao();
		double valorCorrecao = correcao.get(tipoRefeicao);

		double totalInsulina = totalCHO / valorCorrecao;
		
		return totalInsulina;
	}

	public static double getTotalInsulinaFatorCorrecao(Paciente paciente, Glicemia glicemia) {
		DadosMedicos fatorCorrecao = paciente.getFatorCorrecao();
		DadosMedicos glicemiaAlvoDadosMedicos = paciente.getGlicemiaAlvo();
		TipoRefeicao tipoRefeicao = glicemia.getTipoRefeicao();
		double valorFatorCorrecao = fatorCorrecao.get(tipoRefeicao);
		double glicemiaAlvo = glicemiaAlvoDadosMedicos.get(tipoRefeicao);

		double totalInsulina = (glicemia.getValorGlicemia() - glicemiaAlvo) / valorFatorCorrecao;

		return totalInsulina < 0 ? 0 : totalInsulina;
	}
}
