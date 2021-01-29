
@Service
public class PessoaService {


    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa atualizar(Long codigo, Pessoa pessoa){
        Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
		if (pessoaSalva == null){
			throw new EmptyResultDataAccessException(1);
		}
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
        return pessoaRepository.save(pessoaSalva);
    }
}