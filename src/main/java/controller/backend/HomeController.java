package controller.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Curso;
import model.Grade;
import model.Pessoa;
import model.PessoaCurso;
import model.estrutura.DadosGerais;
import controller.util.EstruturaArvore;
import controller.util.UsuarioController;
import dao.Interface.CursoDAO;



@Named
@ViewScoped
public class HomeController implements Serializable {

	//========================================================= VARIABLES ==================================================================================//

	private static final long serialVersionUID = 1L;
	private CursoDAO cursoDAO ;
	private Boolean aparecerTabela = false;
	private Curso curso = new Curso();
	private EstruturaArvore estruturaArvore;
	private List<DadosGerais> listaDadosGerais = new ArrayList<DadosGerais>();
	private List<DadosGerais> listaDadosGeraisCurso = new ArrayList<DadosGerais>();
	
	
	//========================================================= METODOS ==================================================================================//

	@Inject
	private UsuarioController usuarioController;

	@PostConstruct
	public void init() {
		estruturaArvore = EstruturaArvore.getInstance();
		cursoDAO  = estruturaArvore.getCursoDAO();
				
		if (usuarioController.getAutenticacao().getTipoAcesso().equals("aluno")){	
			aparecerTabela = false;
		}
		else{
			//curso = cursoDAO.buscarPorCodigo(usuarioController.getAutenticacao().getCursoSelecionado().getCodigo());	
			aparecerTabela = true;
			gerarDados();
		}		
		
		
	}
	
	
	public void gerarDados (){
		
		listaDadosGerais = new ArrayList<DadosGerais>();		
		Pessoa pessoa = usuarioController.getAutenticacao().getPessoa();		
		for (PessoaCurso pessoaCurso : pessoa.getPessoaCurso()){
			curso = cursoDAO.buscarPorCodigo(pessoaCurso.getCurso().getCodigo());
			DadosGerais dadosGeraisCurso = new DadosGerais();				
			dadosGeraisCurso.setCodigoCurso(curso.getCodigo());
			dadosGeraisCurso.setAlunosTotaisCurso(curso.getGrupoAlunos().size());
			dadosGeraisCurso.setDataUltimaImportacao(curso.getDataAtualizacao());
			
			listaDadosGeraisCurso.add(dadosGeraisCurso);		
			
			
			for(Grade grade : curso.getGrupoGrades()){			
				
				DadosGerais dadosGerais = new DadosGerais();				
				dadosGerais.setCodigoCurso(curso.getCodigo());
				dadosGerais.setAlunosTotaisCurso(curso.getGrupoAlunos().size());
				dadosGerais.setDataUltimaImportacao(curso.getDataAtualizacao());
				dadosGerais.setGradeCurso(grade.getCodigo());
				dadosGerais.setQuantidadeAlunosGrade(grade.getGrupoAlunos().size());
				listaDadosGerais.add(dadosGerais);				
				
			}			
			
		}		
		
	}
	
	
	

	public CursoDAO getCursoDAO() {
		return cursoDAO;
	}

	public void setCursoDAO(CursoDAO cursoDAO) {
		this.cursoDAO = cursoDAO;
	}

	public Boolean getAparecerTabela() {
		return aparecerTabela;
	}

	public void setAparecerTabela(Boolean aparecerTabela) {
		this.aparecerTabela = aparecerTabela;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public EstruturaArvore getEstruturaArvore() {
		return estruturaArvore;
	}

	public void setEstruturaArvore(EstruturaArvore estruturaArvore) {
		this.estruturaArvore = estruturaArvore;
	}

	public UsuarioController getUsuarioController() {
		return usuarioController;
	}

	public void setUsuarioController(UsuarioController usuarioController) {
		this.usuarioController = usuarioController;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public List<DadosGerais> getListaDadosGerais() {
		return listaDadosGerais;
	}


	public void setListaDadosGerais(List<DadosGerais> listaDadosGerais) {
		this.listaDadosGerais = listaDadosGerais;
	}


	public List<DadosGerais> getListaDadosGeraisCurso() {
		return listaDadosGeraisCurso;
	}


	public void setListaDadosGeraisCurso(List<DadosGerais> listaDadosGeraisCurso) {
		this.listaDadosGeraisCurso = listaDadosGeraisCurso;
	}

	
	
	

	

	//========================================================= GET - SET ==================================================================================//

	
}