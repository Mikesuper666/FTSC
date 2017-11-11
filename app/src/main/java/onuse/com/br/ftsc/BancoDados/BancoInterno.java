package onuse.com.br.ftsc.BancoDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by maico on 27/09/17.
 */
public class BancoInterno extends SQLiteOpenHelper {
    public BancoInterno(Context context){
        super(context, "u726847299_tcc", null, 2);
        //contexto, nome do banco, cursor factoyry, versao do banco
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE if not EXISTS nome_linha" +
                        "(_id INTEGER AUTO_INCREMENT," +
                        "linha VARCHAR (100)," +
                        "imagem INTEGER(11),"+
                        "imagem_destino INTEGER(11),"+
                        "codigos VARCHAR(50)," +
                        "PRIMARY KEY (_id));"
        );

        /*db.execSQL( "INSERT INTO `nome_linha` (`_id`, `linha`, `imagem`, `codigos`) VALUES"+
                "(0, '11 DE ABRIL', 58, '31, 44, 351, 414'),"+
                "(1, 'ALVORADA', 14, '1, 401'),"+
                "(2, 'ALVORADA ANCHIETA', 14, '6, 491'),"+
                "(3, 'ALVORADA CAIRU', 14, '3, 403'),"+
                "(4, 'ALVORADA FREEWAY', 14, '2, 48, 15, 415, 470'),"+
                "(5, 'ALVORADA GRAVATAI', 8, '220'),"+
                "(6, 'ALVORADA GRAVATAI ULBRA', 8, '71, 221, 223'),"+
                "(7, 'ALVORADA GRAVATAI FERRAMENTAS GERAIS', 0, '223'),"+
                "(8, 'ALVORADA IPIRANGA', 10, '516, 104'),"+
                "(9, 'ALVORADA IPIRANGA PUC VILA ELZA', 12, '139'),"+//editar
                "(10, 'ALVORADA ITARARÉ', 11, '138'),"+
                "(11, 'ALVORADA PROTASIO', 12, '21, 421'),"+
                "(12, 'ALVORADA PROTÁSIO IPA VILA ELZA', 12, '143'),"+//editar
                "(13, 'ALVORADA PROTÁSIO JARDIN POA', 12, '155'),"+//EDITAR
                "(14, 'ALVORADA PUC', 10, '125, 535'),"+
                "(15, 'ALVORADA SÃO BORJA', 13, '151'),"+
                "(16, 'ALVORADA SERTORIO', 14, '4, 18, 50, 404'),"+
                "(17, 'ALVORADA SERTORIO CAIRU', 14, '126'),"+
                "(18, 'ALVORADA VIAMAO', 3, '185, 361, 275'),"+
                "(19, 'AMERICANA', 15, '70, 454'),"+
                "(20, 'AMERICANA ANCHIETA', 15, '79, 382, 458'),"+
                "(21, 'AMERICANA CAIRU', 15, '72, 455'),"+
                "(22, 'AMERICANA CEE', 16, '74, 396'),"+
                "(23, 'AMERICANA CEE FREEWAY', 16, '78, 382, 471, 71'),"+
                "(24, 'AMERICANA FREEWAY', 15, '26, 52, 75, 103'),"+
                "(25, 'AMERICANA PORTO RICO', 1, '153'),"+//**************************************
                "(26, 'AMERICANA RÁPIDA SERTÓRIO', 15, '192'),"+
                "(27, 'AMERICANA SALOMÉ PDA40', 1, '178'),"+//**************************************
                "(28, 'CABRAL FREDERICO DHIL', 18, '38, 312, 438'),"+
                "(29, 'CEDRO', 1, '42, 312, 438'),"+//**************************************
                "(30, 'DISTRITO INDUSTRIAL BALTAZAR', 24, '240, 364, 427'),"+
                "(31, 'DISTRITO INDUSTRIAL CACHOEIRINHA', 19, '242, 300'),"+
                "(32, 'DISTRITO INDUSTRIAL CACHOEIRINHA FIGUEIRA', 20, '242, 300'),"+
                "(33, 'DISTRITO INDUSTRIAL CACHOEIRINHA P.47', 26, '242, 300'),"+
                "(34, 'DISTRITO INDUSTRIAL CIRCULAR ALVORADA', 23, '251, 249'),"+
                "(35, 'DISTRITO INDUSTRIAL PDA 40', 24, '241, 365'),"+
                "(36, 'ESTANCIA GRANDE', 29, '230, 355, 426'),"+
                "(37, 'ESTANCIA GRANDE CAIRU', 29, '270, 359, 429'),"+//**************************************
                "(38, 'ESTANCIA GRANDE (7500-7501)', 29, '7199'),"+//**************************************
                "(39, 'ESTANCIA GRANDE FERRAMENTAS GERAIS PDA 40', 30, '233'),"+
                "(40, 'ESTANCIA GRANDE PDA 40', 31, '232'),"+
                "(41, 'EXECUTIVO ALVORADA ASSIS BRASIL', 14, '16, 49, 416'),"+
                "(42, 'EXECUTIVO ALVORADA FREEWAY', 14, '15'),"+
                "(43, 'EXECUTIVO ALVORADA SERTÓRIO', 14, '18'),"+
                "(44, 'EXECUTIVO AMERICANA', 15, '75'),"+
                "(45, 'EXECUTIVO JARDIN ALGARVE ASSIS BRASIL', 51, '85, 53, 374, 473'),"+
                "(46, 'EXECUTIVO JARDIN ALGARVE FREEWAY', 15, '84'),"+
                "(47, 'EXECUTIVO JARDIN JARDIN POA', 52, '194, 94, 55'),"+
                "(48, 'EXECUTIVO JARDIN JARDIN POA JARDIN PORTO VERDE', 1, '70'),"+//editar
                "(49, 'EXECUTIVO JARDIN JARDIN APARECIDA', 41, '205, 282, 506, 67'),"+
                "(50, 'EXECUTIVO JARDIN POA', 52, '95, 337, 497'),"+
                "(52, 'EXECUTIVO P FIGUEIRA', 61, '64, 68, 327, 453'),"+
                "(53, 'EXECUTIVO PORTO VERDE', 70, '70, 194, 195, 529'),"+
                "(54, 'EXECUTIVO PORTO VERDE JARDIN ALGARVE', 55, '115'),"+
                "(55, 'EXECUTIVO SALOMÉ ITARARÉ', 74, '170'),"+
                "(56, 'EXECUTIVO UMBU BALTAZAR', 1, '535'),"+//editar
                "(57, 'EXECUTIVO VILA ELZA', 100, '113'),"+
                "(58, 'FERNANDO FERRARI', 32, '41, 315, 441'),"+
                "(59, 'FERNANDO FERRARI PROTASIO ALVES', 32, '158, 159'),"+//editar
                "(60, 'FORMOSA', 35, '10, 410'),"+
                "(61, 'FORMOSA MARINGA', 36, '12'),"+
                "(62, 'INTERSUL', 37, '265, 363, 428'),"+
                "(63, 'INTERSUL FLORIANO PEIXOTO', 37, '264'),"+
                "(64, 'JARDIN ALGARVE', 51, '80, 329, 459'),"+
                "(65, 'JARDIN ALGARVE ANCHIETA', 51, '96, 283, 500'),"+
                "(66, 'JARDIN ALGARVE CAIRU', 51, '82, 330, 460'),"+
                "(67, 'JARDIN ALGARVE FREEWAY', 51, '54, 84, 373, 472'),"+
                "(68, 'JARDIN ALGARVE JARDIN POA', 54, '88, 333, 463'),"+
                "(69, 'JARDIN ALGARVE JARDIN POA PORTO VERDE', 55, '91'),"+
                "(70, 'JARDIN ALGARVE JARDIN POA PORTO VERDE TERRA NOVA', 51, '585'),"+//**************************************
                "(71, 'JARDIN ALGARVE PORTO VERDE', 55, '127'),"+
                "(72, 'JARDIN ALGARVE PORTO VERDE ANCHIETA', 55, '144'),"+
                "(73, 'JARDIN ALGARVE PORTO VERDE CAIRU', 55, '131, 538'),"+
                "(74, 'JARDIN ALGARVE SERTORIO', 51, '83, 331, 461'),"+
                "(75, 'JARDIN ALVORADA', 38, '210, 350, 413'),"+
                "(76, 'JARDIN ALVORADA INTERSUL', 39, '57, 369, 420'),"+
                "(77, 'JARDIN ALVORADA BH', 40, '208, 393, 482'),"+
                "(78, 'JARDIN ALVORADA FLORIANO PEIXOTO', 34, '262, 215, 532'),"+
                "(79, 'JARDIN ALVORADA PROTÁSIO ALVES', 107, '58, 209, 278, 507'),"+
                "(80, 'JARDIN APARECIDA', 41, '200, 346, 402'),"+
                "(81, 'JARDIN APARECIDA ANCHIETA', 41, '209'),"+
                "(82, 'JARDIN APARECIDA BH', 43, '201, 390, 481'),"+
                "(83, 'JARDIN APARECIDA CAIRU', 41, '202, 347, 406'),"+
                "(84, 'JARDIN APARECIDA FREE WAY', 41, '148'),"+
                "(85, 'JARDIN APARECIDA IGUATEMI', 46, '207, 392, 488'),"+
                "(86, 'JARDIN APARECIDA IPIRANGA', 48, '216, 219, 541'),"+
                "(87, 'JARDIN APARECIDA PROTÁSIO ALVES', 49, '204, 349, 412'),"+
                "(88, 'JARDIN APARECIDA PROTÁSIO ALVES BH', 44, '206, 391, 486'),"+
                "(89, 'JARDIN APARECIDA PDA 40', 1, '152, 338, 339'),"+//editar
                "(90, 'JARDIN APARECIDA RÁPIDA SERTÓRIO', 41, '193'),"+
                "(91, 'JARDIN APARECIDA SAÍDA 6425 FERNANDO DHILL', 41, '254'),"+
                "(92, 'JARDIN APARECIDA SERTÓRIO', 41, '203, 348, 411'),"+
                "(93, 'JARDIN APARECIDA SERTÓRIO CAIRU', 41, '120'),"+
                "(94, 'JARDIN APARECIDA SERVIDÃO', 42, '212, 213, 531'),"+
                "(95, 'JARDIN APARECIDA VIA 6425 FERNANDO DHIL', 41, '253'),"+
                "(96, 'JARDIN PORTO ALEGRE', 70, '90, 334, 464'),"+
                "(97, 'JARDIN PORTO ALEGRE ANCHIETA', 52, '97, 284, 501'),"+
                "(98, 'JARDIN PORTO ALEGRE ANDRADE NEVES', 53, '111, 112'),"+
                "(99, 'JARDIN PORTO ALEGRE PROTÁSIO ALVES', 12, '121'),"+
                "(100, 'JARDIN PORTO ALEGRE J ALG PV STELLA MARIS J APARECIDA BH FAPA', 50, '173'),"+
                "(101, 'JARDIN PORTO ALEGRE J ALG STELLA M J APARECIDA', 50, '87'),"+//editar
                "(102, 'JARDIN PORTO ALEGRE CAIRU', 52, '92, 335, 465'),"+
                "(103, 'JARDIN PORTO ALEGRE RÁPIDA SERTÓRIO', 52, '231'),"+
                "(104, 'JARDIN PORTO ALEGRE SERTÓRIO', 52, '93, 336, 466'),"+
                "(105, 'MARINGA', 57, '8, 302, 408'),"+
                "(106, 'MARINGA FORMOSA', 36, '12, 490'),"+
                "(107, 'NOVA AMERICANA', 1, '142'),"+//editar
                "(108, 'PASSO FIGUEIRA', 59, '30, 306, 430'),"+
                "(109, 'PASSO FIGUEIRA AMERICANA', 60, '29'),"+
                "(110, 'PASSO FIGUEIRA CAIRU', 59, '32, 307, 432'),"+
                "(111, 'PASSO FIGUEIRA FERNANDO FERRARI', 62, '37, 309, 431, 434'),"+
                "(112, 'PASSO FIGUEIRA FREE WAY', 60, '33, 292, 497'),"+
                "(113, 'PASSO FIGUEIRA IGUATEMI', 63, '50, 297, 485'),"+
                "(114, 'PASSO FIGUEIRA IPIRANGA', 59, '26, 380, 424'),"+
                "(115, 'PASSO FIGUEIRA IPIRANGA PUC', 59, '14'),"+
                "(116, 'PASSO FIGUEIRA NOVA ALVORADA', 64, '100, 287, 504'),"+
                "(117, 'PASSO FIGUEIRA NOVA ALVORADA PAR', 64, '160'),"+//editar
                "(118, 'PASSO FIGUEIRA PROTÁSIO ALVES', 59, '25, 305, 425'),"+
                "(119, 'PASSO FIGUEIRA PROTÁSIO ALVES CARLOS GOMES', 59, '31'),"+
                "(120, 'PASSO FIGUEIRA PUC', 59, '14, 296, 400'),"+
                "(121, 'PASSO FIGUEIRA SÃO PEDRO', 66, '49, 322, 448'),"+
                "(122, 'PASSO FIGUEIRA SÃO PEDRO P.ALVES', 66, '27'),"+
                "(123, 'PASSO FIGUEIRA SÃO PEDRO P.ALVES PAR', 66, '162'),"+
                "(124, 'PASSO FIGUEIRA SÃO PEDRO FERNANDO FERRARI', 67, '24, 27, 291, 496'),"+
                "(125, 'PASSO FIGUEIRA SÃO PEDRO FERNANDO FERRARI PAR', 67, '166'),"+
                "(126, 'PASSO FIGUEIRA VIDA NOVA NOVA ALVORADA', 69, '99'),"+
                "(127, 'PASSO FIGUEIRA VIDA NOVA', 68, '76, 79, 298, 489'),"+
                "(128, 'PASSO FIGUEIRA VIDA NOVA PAR', 68, '164'),"+
                "(129, 'PINDORAMA', 1, '182, 366, 375'),"+//editar
                "(130, 'PORTO VERDE', 70, '81, 381, 477'),"+
                "(131, 'PORTO VERDE J ALGARVE J POA', 55, '115, 116, 525'),"+
                "(132, 'PORTO VERDE J ALGARVE J POA CASTELO BCO', 55, '135, 136, 139'),"+//editar
                "(133, 'PORTO VERDE J ALGARVE J POA PDA', 1, '172'),"+//editar
                "(134, 'PORTO VERDE SERTÓRIO', 70, '98, 285, 502'),"+
                "(135, 'PORTO VERDE SERTÓRIO CAIRU', 70, '119, 123, 527'),"+
                "(136, 'SALOMÉ', 71, '45, 319, 445'),"+
                "(137, 'SALOMÉ ANCHIETA', 72, '55, 360, 450'),"+
                "(138, 'SALOMÉ CAIRU', 71, '46, 320, 446'),"+
                "(139, 'SALOMÉ PDA 40', 79, '180'),"+
                "(140, 'SALOMÉ PROTÁSIO ALVES', 71, '53, 274, 511'),"+
                "(141, 'SALOMÉ RÁPIDA SERTÓRIO', 71, '255'),"+
                "(142, 'SALOMÉ SERTÓRIO', 71, '47, 321, 447'),"+
                "(143, 'SALOMÉ UMBU', 75, '52, 263'),"+
                "(144, 'SALOMÉ UMBU FAPA', 1, '179'),"+//editar
                "(145, 'SANTA CLARA', 76, '7, 368, 407'),"+
                "(146, 'SÃO PEDRO', 79, '40, 314, 440'),"+
                "(147, 'SÃO PEDRO CEASA', 77, '36, 311, 436'),"+
                "(148, 'SÃO PEDRO FERRARI', 78, '34, 41, 315, 441'),"+
                "(149, 'SÃO PEDRO FERRARI PROTÁSIO ALVES', 78, '158, 549'),"+
                "(150, 'SÃO PEDRO MARINGA PDA 40', 79, '190'),"+//editar
                "(151, 'SÃO PEDRO PDA', 79, '181, 344, 345'),"+
                "(152, 'SÃO PEDRO  RUI RAMOS', 108, '140, 150, 540'),"+
                "(153, 'SERVIDÃO', 42, '211'),"+
                "(154, 'STELLA MARIS', 86, '427280, 362, 437'),"+
                "(155, 'STELLA MARIS BIG', 81, '17, 377, 378'),"+
                "(156, 'STELLA MARIS JARDIN APARECIDA', 82, '217, 353, 422'),"+
                "(157, 'STELLA MARIS JARDIN APARECIDA CAIRU', 82, '218, 376, 423'),"+
                "(158, 'STELLA MARIS PDA 40', 83, '184'),"+
                "(159, 'STELLA MARIS SERTÓRIO', 84, '281, 279, 508'),"+
                "(160, 'STELLA MARIS SERTÓRIO CAIRU', 84, '117, 129, 534'),"+
                "(161, 'STELLA MARIS TERRA NOVA', 84, '259'),"+
                "(162, 'SUMARÉ', 87, '73, 328, 456'),"+
                "(163, 'SUMARÉ NOVA AMERICANA', 88, '142'),"+
                "(164, 'TAIMBÉ', 89, '303, 409'),"+
                "(165, 'TAIMBÉ AMERICANA PROTÁSIO ALVES', 1, '146'),"+//editar
                "(166, 'TAIMBÉ DUQUE DE CAXIAS', 90, '19, 9'),"+
                "(167, 'TAIMBÉ FORMOSA MARINGA', 36, '304' ),"+//EDITAR
                "(168, 'TIJUCA', 91, '105, 106, 521'),"+
                "(169, 'TM1', 92, '953, 954'),"+//editar
                "(170, 'TM2', 92, '955'),"+//editar
                "(171, 'TM5', 92, '710, 951, 952'),"+//editar
                "(172, 'TM5 VILA ELZA', 92, '713, 521'),"+//editar
                "(173, 'UMBU', 96, '39, 313, 439'),"+
                "(174, 'UMBU ANCHIETA AMERICANA', 97, '54, 324, 433'),"+
                "(175, 'UMBU ANCHIETA BALTAZAR', 97, '109, 110, 523'),"+
                "(176, 'UMBU CAIRU', 96, '43, 317, 443'),"+
                "(177, 'UMBU CAIRU SERTÓRIO', 97, '107, 105, 522'),"+
                "(178, 'UMBU FAPA', 98, '183'),"+//editar
                "(179, 'UMBU IPIRANGA', 96, '20, 267, 515'),"+
                "(180, 'UMBU PROTÁSIO ALVES', 96, '56, 352, 417'),"+
                "(181, 'UMBU RÁPIDA SERTÓRIO', 96, '257'),"+
                "(182, 'UMBU SERTÓRIO', 97, '48, 318, 444'),"+
                "(183, 'UMBU SERTÓRIO CAIRU', 97, '107'),"+
                "(184, 'VILA ELZA', 99, '51, 60, 323, 449'),"+
                "(185, 'VILA ELZA AMERICANA', 100, '71, 371'),"+
                "(186, 'VILA ELZA ANCHIETA', 102, '59, 295, 493'),"+
                "(187, 'VILA ELZA BIG', 99, '245, 357, 358'),"+
                "(188, 'VILA ELZA BIG AMERICANA', 100, '247, 374, 372'),"+
                "(189, 'VILA ELZA BIG FADERGS', 99, '576'),"+//**************************************
                "(190, 'VILA ELZA ZIVI', 99, '250, 385, 386'),"+
                "(191, 'VILA ELZA CAIRU', 99, '62, 327, 453'),"+
                "(192, 'VILA ELZA FAPA', 103, '188, 189'),"+
                "(193, 'VILA ELZA FREE WAY', 100, '66, 69, 383, 474'),"+
                "(194, 'VILA ELZA IPIRANGA', 12, '68, 387, 480'),"+//editar
                "(195, 'VILA ELZA IPIRANGA SÃO PEDRO', 12, '101, 271, 513'),"+//editar
                "(196, 'VILA ELZA PDA 40', 99, '185, 244'),"+//editar
                "(197, 'VILA ELZA PIRATINI UMBU AMERICANA', 106, '394, 65'),"+
                "(198, 'VILA ELZA PROTÁSIO ALVES', 99, '67, 384, 479'),"+
                "(199, 'VILA ELZA P.ALVES SÃO PEDRO', 105, '102, 288, 505'),"+
                "(200, 'VILA ELZA PUC', 12, '28, 277, 509'),"+
                "(201, 'VILA ELZA RÁPIDA SERTÓRIO', 99, '256'),"+
                "(202, 'VILA ELZA SERTÓRIO', 99, '63, 326, 452'),"+
                "(203, 'VILA ELZA SERTÓRIO CAIRU', 99, '118, 122, 526'),"+
                "(204, 'VILA ELZA PIRATINI', 104, '260'),"+
                "(205, 'EMN X CENTRO', 27, '800'),"+
                "(206, 'EMN X CAIRU', 28, '801'),"+
                "(207, 'TREINAMENTO', 1, '935');"
        );*/
        db.execSQL(
                "CREATE TABLE if not EXISTS d_execoes " +
                        "(_id INTEGER AUTO_INCREMENT," +
                        "nome VARCHAR (100)," +
                        "tipo_execao INTEGER(11),"+
                        "funcao INTEGER(11),"+
                        "horario INTEGER(11),"+
                        "qtde_ocorrencias INTEGER(11),"+
                        "PRIMARY KEY (_id));"
        );

        db.execSQL(
                "CREATE TABLE if not EXISTS ocorrencias " +
                        "(_id VARCHAR(50)," +
                        "matricula_func INTEGER(11)," +
                        "matricula_fiscal INTEGER(11)," +
                        "ocorrencia VARCHAR(50),"+
                        "PRIMARY KEY (_id));"
        );

       /* db.execSQL( "INSERT INTO `d_execoes` (`_id`, `nome`, `tipo_execao`) VALUES" +
                "(4185, 'João Fulano', 0),"+
                "(5261, 'Fulano Cavalo', 0),"+
                "(5265, 'Nome Sobrenome', 0),"+
                "(5523, 'Nome Sobrenome', 0),"+
                "(5736, 'Nome Sobrenome', 0),"+
                "(5981, 'Nome Sobrenome', 0),"+
                "(6363, 'Nome Sobrenome', 0),"+
                "(6511, 'Nome Sobrenome', 0),"+
                "(6591, 'Nome Sobrenome', 0),"+
                "(7015, 'Nome Sobrenome', 0),"+
                "(7639, 'Nome Sobrenome', 0),"+
                "(7696, 'Nome Sobrenome', 0),"+
                "(7817, 'Nome Sobrenome', 0),"+
                "(7836, 'Nome Sobrenome', 0),"+
                "(7878, 'Nome Sobrenome', 0),"+
                "(4375, 'Nome Sobrenome', 1),"+
                "(6956, 'Nome Sobrenome', 1),"+
                "(7897, 'Nome Sobrenome', 1),"+
                "(26864, 'Nome Sobrenome', 1);"
        );*/

        db.execSQL(
                "CREATE TABLE if not EXISTS tipo_carro " +
                        "(_id INTEGER AUTO_INCREMENT," +
                        "tipoCarro INTEGER (11)," +
                        "d_adaptado INTEGER(11),"+
                        "qtde_ocorrencias INTEGER(11),"+
                        "PRIMARY KEY (_id));"
        );

        db.execSQL(
                "CREATE TABLE if not EXISTS carro_ocorrencias " +
                        "(_id VARCHAR(50)," +
                        "codigo INTEGER(11)," +
                        "matricula_fiscal INTEGER(11)," +
                        "ocorrencia VARCHAR(50),"+
                        "PRIMARY KEY (_id));"
        );

       /* db.execSQL( "INSERT INTO `tipo_carro` (`_id`, `tipoCarro`, `d_adaptado`) VALUES" +
                "(2603, 0, 1),"+
                "(2613, 0, 1),"+
                "(7003, 1, 1),"+
                "(7004, 1, 1),"+
                "(7146, 1, 1),"+
                "(7147, 1, 1),"+
                "(7179, 1, 1),"+
                "(7180, 1, 1),"+
                "(7181, 1, 1),"+
                "(7182, 1, 1),"+
                "(7183, 1, 1),"+
                "(7184, 1, 1),"+
                "(7185, 1, 1),"+
                "(7186, 1, 1),"+
                "(7187, 1, 1),"+
                "(7188, 1, 1),"+
                "(7189, 1, 1),"+
                "(7190, 1, 1),"+
                "(7191, 1, 1),"+
                "(7192, 1, 1),"+
                "(7193, 1, 1),"+
                "(7194, 1, 1),"+
                "(7195, 1, 1),"+
                "(7196, 1, 1),"+
                "(7197, 1, 1),"+
                "(7199, 1, 1),"+
                "(7200, 1, 1),"+
                "(7201, 1, 1),"+
                "(7202, 1, 1),"+
                "(7203, 1, 1),"+
                "(7204, 1, 1),"+
                "(7205, 1, 1),"+
                "(7206, 1, 1),"+
                "(7207, 1, 1),"+
                "(7208, 1, 1),"+
                "(7209, 1, 1),"+
                "(7210, 1, 1),"+
                "(7211, 1, 1),"+
                "(7212, 1, 1),"+
                "(7213, 1, 1),"+
                "(7214, 1, 1),"+
                "(7215, 1, 1),"+
                "(7216, 1, 1),"+
                "(7217, 1, 1),"+
                "(7218, 1, 1),"+
                "(7219, 1, 1),"+
                "(7220, 1, 1),"+
                "(7221, 1, 1),"+
                "(7222, 1, 1),"+
                "(7223, 1, 1),"+
                "(7224, 1, 1),"+
                "(7225, 1, 1),"+
                "(7226, 1, 1),"+
                "(7227, 1, 1),"+
                "(7228, 1, 1),"+
                "(7701, 2, 1),"+
                "(7703, 2, 1),"+
                "(7704, 2, 1),"+
                "(7705, 2, 1),"+
                "(7706, 2, 1),"+
                "(7707, 2, 1),"+
                "(7708, 2, 1),"+
                "(7709, 2, 1),"+
                "(7710, 2, 1),"+
                "(7711, 2, 1),"+
                "(7712, 2, 1),"+
                "(7716, 2, 1),"+
                "(7717, 2, 1),"+
                "(7401, 3, 1),"+
                "(7402, 3, 1),"+
                "(7403, 3, 1),"+
                "(7404, 3, 1),"+
                "(7405, 3, 1),"+
                "(7406, 3, 1),"+
                "(7407, 3, 1),"+
                "(7408, 3, 1),"+
                "(7458, 3, 1),"+
                "(7459, 3, 1),"+
                "(7460, 3, 1),"+
                "(7461, 3, 1),"+
                "(7462, 3, 1),"+
                "(7463, 3, 1),"+
                "(7464, 3, 1),"+
                "(7465, 3, 1),"+
                "(7470, 3, 1),"+
                "(7471, 3, 1),"+
                "(7472, 3, 1),"+
                "(7473, 3, 1),"+
                "(7474, 3, 1),"+
                "(7475, 3, 1),"+
                "(7476, 3, 1),"+
                "(7477, 3, 1),"+
                "(7478, 3, 1),"+
                "(7479, 3, 1),"+
                "(7480, 3, 1),"+
                "(7481, 3, 1),"+
                "(7482, 3, 1),"+
                "(7483, 3, 1),"+
                "(7484, 3, 1),"+
                "(7485, 3, 1),"+
                "(7486, 3, 1),"+
                "(7487, 3, 1),"+
                "(7488, 3, 1),"+
                "(7489, 3, 1),"+
                "(7490, 3, 1),"+
                "(7491, 3, 1),"+
                "(7492, 3, 1),"+
                "(7493, 3, 1),"+
                "(7494, 3, 1);"
);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table nome_linha;");
        db.execSQL("drop table d_execoes;");
        db.execSQL("drop table ocorrencias;");
        db.execSQL("drop table tipo_carro;");
        db.execSQL("drop table carro_ocorrencias;");
        onCreate(db);
    }
}