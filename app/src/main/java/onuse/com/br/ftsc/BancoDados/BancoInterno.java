package onuse.com.br.ftsc.BancoDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by maico on 27/09/17.
 */
public class BancoInterno extends SQLiteOpenHelper {
    public BancoInterno(Context context){
        super(context, "Soul", null, 1);
        //contexto, nome do banco, cursor factoyry, versao do banco
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE if not EXISTS nome_linha " +
                        "(_id INTEGER PRIMARY KEY," +
                        "linha VARCHAR (100)," +
                        "imagem INTEGER(11));"
        );

        db.execSQL( "INSERT INTO `nome_linha` (`_id`, `linha`, `imagem`) VALUES"+
                "(1, 'ALVORADA', 14),"+//**************************************
                "(2, 'ALVORADA FREEWAY', 2),"+//**************************************
                "(3, 'ALVORADA CAIRU', 3),"+//**************************************
                "(4, 'ALVORADA SERTORIO', 4),"+//**************************************
                "(6, 'ALVORADA ANCHIETA', 5),"+//**************************************
                "(7, 'SANTA CLARA', 77),"+
                "(8, 'MARINGA', 57),"+
                "(9, 'TAIMBÉ-DUQUE DE CAXIAS', 90),"+
                "(10, 'FORMOSA', 35),"+
                "(12, 'MARINGA-FORMOSA', 36),"+
                "(14, 'P.FIGUEIRA-IPIRANGA-PUC', 1),"+//**************************************
                "(15, 'ALVORADA FREEWAY', 1),"+//**************************************
                "(16, 'ALVORADA EXECUTIVO ASSIS BRASIL', 1),"+
                "(17, 'STELLA MARIS-BIG', 81),"+
                "(19, 'TAIMBÉ-DUQUE DE CAXIAS', 90),"+
                "(20, 'UMBU-IPIRANGA', 1),"+//**************************************
                "(21, 'ALVORADA PROTASIO', 12),"+
                "(24, 'P.FIGUEIRA-SÃO PEDRO-F.FERRARI', 67),"+
                "(25, 'P.FIGUEIRA-P.ALVES', 1),"+//**************************************
                "(26, 'AMERICANA FREEWAY', 1),"+//**************************************
                "(27, 'P.FIGUEIRA-SÃO PEDRO-P.ALVES', 1),"+//**************************************
                "(28, 'VILA ELZA-PUC', 1),"+//**************************************
                "(29, 'P.FIGUEIRA-AMERICANA', 60),"+
                "(30, 'P.FIGUEIRA', 59),"+
                "(31, 'P.FIGUEIRA-P.ALVES-CARLOS GOMES', 1),"+//**************************************
                "(32, 'P.FIGUEIRA-CAIRU', 1),"+//**************************************
                "(33, 'P.FIGUEIRA-FREE WAY', 1),"+//**************************************
                "(34, 'SÃO PEDRO-FERRARI', 78),"+
                "(36, 'SÃO PEDRO-CEASA', 77),"+
                "(37, 'P.FIGUEIRA-F.FERRARI', 62),"+
                "(38, 'CABRAL', 18),"+
                "(39, 'UMBU', 96),"+
                "(40, 'STELLA MARIS-PDA-40', 83),"+
                "(41, 'SÃO PEDRO-FERRARI', 78),"+
                "(43, 'UMBU-CAIRU', 1),"+//**************************************
                "(44, '11 DE ABRIL', 58),"+
                "(45, 'SALOMÉ', 71),"+
                "(46, 'SALOMÉ-CAIRU', 1),"+//**************************************
                "(47, 'SALOMÉ-SERTÓRIO', 1),"+//**************************************
                "(48, 'ALVORADA FREEWAY', 1),"+//**************************************
                "(49, 'ALVORADA EXECUTIVO ASSIS BRASIL', 1),"+//**************************************
                "(50, 'P.FIGUEIRA-IGUATEMI', 64),"+
                "(51, 'VILA ELZA', 99),"+
                "(52, 'SALOMÉ-UMBU', 75),"+
                "(53, 'ALGARVE EXEC. ASSIS BRASIL', 1),"+//**************************************
                "(54, 'UMBU-ANCHIETA-AMERICANA', 97),"+
                "(55, 'ALGARVE J.POA EXEC A/B', 1),"+//**************************************
                "(56, 'UMBU-P.ALVES', 1),"+//**************************************
                "(57, 'ALVORADA INTERSUL', 37),"+
                "(59, 'VILA ELZA-ANCHIETA', 102),"+
                "(60, 'VILA ELZA', 99),"+
                "(61, 'P.FIGUEIRA-VIDA NOVA-FAIXA', 1),"+//**************************************
                "(62, 'VILA ELZA-CAIRU', 1),"+//**************************************
                "(63, 'VILA ELZA-SERTÓRIO', 1),"+//**************************************
                "(65, 'VILA ELZA-PIRATINI-UMBU-AMERICANA', 106),"+
                "(66, 'VILA ELZA-FREE WAY', 100),"+
                "(67, 'VILA ELZA-PROTÁSIO ALVES', 1),"+//**************************************
                "(68, 'VILA ELZA-IPIRANGA', 1),"+//**************************************
                "(69, 'VILA ELZA-FREE WAY', 100),"+//**************************************
                "(70, 'AMERICANA', 15),"+
                "(71, 'VILA ELZA-AMERICANA', 100),"+
                "(72, 'AMERICANA CAIRU', 1),"+//**************************************
                "(73, 'SUMARÉ', 87),"+
                "(74, 'AMERICANA CEE', 16),"+
                "(75, 'AMERICANA FREEWAY', 1),"+//**************************************
                "(76, 'P.FIGUEIRA-VIDA NOVA', 68),"+
                "(79, 'AMERICANA ANCHIETA', 1),"+//**************************************
                "(80, 'ALGARVE', 51),"+
                "(81, 'PORTO VERDE', 70),"+
                "(82, 'ALGARVE CAIRU', 1),"+//**************************************
                "(83, 'ALGARVE SERTORIO', 1),"+//**************************************
                "(84, 'ALGARVE FREEWAY', 1),"+//**************************************
                "(87, 'J.POA-J.ALG-STELLA M-J.APARECIDA', 56),"+
                "(88, 'ALGARVE J.POA', 1),"+//**************************************
                "(90, 'J.PORTO ALEGRE', 52),"+
                "(92, 'J.POA-CAIRU', 1),"+//**************************************
                "(93, 'J.POA-SERTÓRIO', 1),"+//**************************************
                "(94, 'ALGARVE EXEC. ASSIS BRASIL', 1),"+//**************************************
                "(96, 'ALGARVE ANCHIETA', 1),"+//**************************************
                "(97, 'J.POA-ANCHIETA', 1),"+//**************************************
                "(98, 'PORTO VERDE-SERTÓRIO', 1),"+//**************************************
                "(99, 'P.FIGUEIRA-VIDA NOVA-NOVA ALVORADA', 69),"+
                "(100, 'P.FIGUEIRA-NOVA ALVORADA', 68),"+
                "(101, 'VILA ELZA-IPIRANGA-SÃO PEDRO', 1),"+//**************************************
                "(102, 'VILA ELZA-P.ALVES-SÃO PEDRO', 1),"+//**************************************
                "(103, 'AMERICANA FREEWAY', 1),"+//**************************************
                "(105, 'TIJUCA', 91),"+
                "(106, 'TIJUCA', 91),"+
                "(107, 'UMBU-SERTÓRIO-CAIRU', 1),"+//**************************************
                "(109, 'UMBU-ANCHIETA-BALTAZAR', 1),"+//**************************************
                "(110, 'UMBU-ANCHIETA-BALTAZAR', 1),"+//**************************************
                "(111, 'J.POA-ANDRADE NEVES', 53),"+
                "(112, 'J.POA-ANDRADE NEVES', 53),"+
                "(115, 'PORTO VERDE-JALGARVE-JPOA', 1),"+//**************************************
                "(116, 'PORTO VERDE-JALGARVE-JPOA', 1),"+//**************************************
                "(117, 'STELLA MARIS-SERTÓRIO-CAIRU', 1),"+
                "(118, 'VILA ELZA-SERTÓRIO-CAIRU', 1),"+//**************************************
                "(119, 'PORTO VERDE-SERTÓRIO-CAIRU', 1),"+//**************************************
                "(120, 'J.APARECIDA-SERTÓRIO-CAIRU', 1),"+//**************************************
                "(121, 'J.POA-J.ALGARVE-P.ALVES', 1),"+//**************************************
                "(122, 'VILA ELZA-SERTÓRIO-CAIRU', 1),"+//**************************************
                "(123, 'PORTO VERDE-SERTÓRIO-CAIRU', 1),"+//**************************************
                "(125, 'ALVORADA PUC', 1),"+//**************************************
                "(126, 'ALVORADA SERTORIO CAIRU', 1),"+//**************************************
                "(127, 'ALGARVE J.POA PORTO VERDE ASSIS BRASIL', 1),"+//**************************************
                "(129, 'STELLA MARIS-SERTÓRIO-CAIRU', 1),"+//**************************************
                "(131, 'ALGARVE J.POA PORTO VERDE CAIRU', 1),"+//**************************************
                "(135, 'PORTO VERDE-JALGARVE-JPOA-CASTELO BCO', 1),"+//**************************************
                "(136, 'PORTO VERDE-JALGARVE-JPOA-CASTELO BCO', 1),"+//**************************************
                "(137, 'PORTO VERDE-JALGARVE-JPOA-CASTELO BCO', 1),"+//**************************************
                "(138, 'ALVORADA IATARE', 12),"+
                "(140, 'SÃO PEDRO -RUI RAMOS', 1),"+//**************************************
                "(142, 'NOVA AMERICANA', 1),"+//**************************************
                "(146, 'TAIMBÉ-AMERICANA-P.ALVES', 1),"+//**************************************
                "(148, 'J.APARECIDA-FREE WAY', 1),"+//**************************************
                "(150, 'SÃO PEDRO -RUI RAMOS', 1),"+//**************************************
                "(151, 'ALVORADA SAO BORJA', 13),"+
                "(152, 'J.APARECIDA-PDA 40', 1),"+//**************************************
                "(158, 'SÃO PEDRO-FERRARI-PROTÁSIO ALVES', 1),"+//**************************************
                "(159, 'FERNANDO FERRARI PROTASIO ALVES', 1),"+//**************************************
                "(160, 'P.FIGUEIRA-NOVA ALVORADA-PAR', 64),"+
                "(162, 'P.FIGUEIRA-SÃO PEDRO-P.ALVES-PAR', 1),"+//**************************************
                "(164, 'P.FIGUEIRA-VIDA NOVA-PAR', 68),"+
                "(166, 'P.FIGUEIRA-SÃO PEDRO-F.FERRARI-PAR', 67),"+
                "(172, 'PORTO VERDE-JALGARVE-JPOA-PDA', 1),"+//**************************************
                "(173, 'J.POA-J.ALG-PV-STELLA MARIS-J.APARECIDA-BH-FAPA', 1),"+//**************************************
                "(178, 'AMERICANA P.40 SAL', 1),"+//**************************************
                "(179, 'SALOMÉ-UMBU-FAPA', 1),"+//**************************************
                "(181, 'SÃO PEDRO-PDA', 79),"+
                "(182, 'PINDORAMA', 1),"+//**************************************
                "(183, 'UMBU-FAPA', 98),"+
                "(184, 'STELLA MARIS-PDA', 83),"+
                "(185, 'VILA ELZA-PDA', 1),"+//**************************************
                "(188, 'VILA ELZA-FAPA', 103),"+
                "(189, 'VILA ELZA-FAPA', 103),"+//**************************************
                "(190, 'SÃO PEDRO-MARINGA-PDA', 1),"+//**************************************
                "(193, 'J.APARECIDA-RÁPIDA-SERTÓRIO', 1),"+//**************************************
                "(194, 'ALGARVE J.POA EX. P.VERDE FW', 1),"+//**************************************
                "(200, 'J.APARECIDA', 41),"+
                "(201, 'J.APARECIDA-BH', 43),"+
                "(202, 'J.APARECIDA-CAIRU', 1),"+//**************************************
                "(203, 'J.APARECIDA-SERTÓRIO', 1),"+//**************************************
                "(204, 'J.APARECIDA-PROTÁSIO ALVES', 1),"+//**************************************
                "(206, 'J.APARECIDA-P.ALVES-BH', 44),"+
                "(207, 'J.APARECIDA-IGUATEMI', 46),"+
                "(208, 'ALVORADA INTERSUL B.H', 1),"+//**************************************
                "(209, 'J.APARECIDA-ANCHIETA', 1),"+//**************************************
                "(210, 'ALVORADA', 14),"+
                "(211, 'SERVIDÃO', 1),"+//**************************************
                "(212, 'J.APARECIDA-SERVIDÃO', 42),"+
                "(213, 'J.APARECIDA-SERVIDÃO', 42),"+
                "(216, 'J.APARECIDA-IPIRANGA', 1),"+//**************************************
                "(217, 'STELLA MARIS-J.APARECIDA', 1),"+//**************************************
                "(218, 'STELLA MARIS-J.APARECIDA-CAIRU', 1),"+//**************************************
                "(219, 'J.APARECIDA-IPIRANGA', 48),"+
                "(221, 'ALVORADA GRAVATAI/ULBRA', 2),"+
                "(223, 'ALVORADA GRAVATAI/ULBRA', 2),"+
                "(230, 'ESTANCIA GRANDE', 29),"+
                "(231, 'J.POA-RÁPIDA-SERTÓRIO', 1),"+//**************************************
                "(232, 'ESTANCIA GRANDE P.40', 31),"+
                "(240, 'DISTRITO INDUSTRIAL BALTAZAR', 24),"+
                "(241, 'DISTRITO INDUSTRIAL P.40', 1),"+
                "(242, 'DISTRITO INDUSTRIAL CACHOEIRINHA', 19),"+
                "(244, 'VILA ELZA-PDA', 1),"+//**************************************
                "(245, 'VILA ELZA-BIG', 1),"+//**************************************
                "(247, 'VILA ELZA-BIG-AMERICANA', 1),"+//**************************************
                "(250, 'VILA ELZA-ZIVI', 1),"+//**************************************
                "(251, 'DISTRITO INDUSTRIAL CIRCULAR ALVORADA', 23),"+//**************************************
                "(253, 'J.APARECIDA VIA 6425-F.DHIL', 1),"+//**************************************
                "(254, 'J.APARECIDA SAÍDA 6425-F.DHILL', 1),"+//**************************************
                "(255, 'SALOMÉ-RÁPIDA-SERTÓRIO', 1),"+//**************************************
                "(256, 'VILA ELZA-RÁPIDA-SERTÓRIO', 1),"+//**************************************
                "(257, 'UMBU-RÁPIDA-SERTÓRIO', 1),"+//**************************************
                "(260, 'VILA ELZA-PIRATINI', 104),"+
                "(263, 'SALOMÉ-UMBU', 75),"+
                "(265, 'INTERSUL', 37),"+
                "(267, 'UMBU-IPIRANGA', 1),"+//**************************************
                "(270, 'ESTANCIA GRANDE CAIRU', 1),"+//**************************************
                "(271, 'VILA ELZA-IPIRANGA-SÃO PEDRO', 1),"+//**************************************
                "(274, 'SALOMÉ-PROTÁSIO ALVES', 1),"+//**************************************
                "(277, 'VILA ELZA-PUC', 1),"+//**************************************
                "(279, 'STELLA MARIS-SERTÓRIO', 84),"+
                "(280, 'STELLA MARIS', 86),"+
                "(281, 'STELLA MARIS-SERTÓRIO', 84),"+
                "(283, 'ALGARVE ANCHIETA', 1),"+//**************************************
                "(284, 'J.POA-ANCHIETA', 1),"+//**************************************
                "(285, 'PORTO VERDE-SERTÓRIO', 1),"+//**************************************
                "(287, 'P.FIGUEIRA-NOVA ALVORADA', 64),"+
                "(288, 'VILA ELZA-P.ALVES-SÃO PEDRO', 1),"+//**************************************
                "(291, 'P.FIGUEIRA-SÃO PEDRO-F.FERRARI', 67),"+
                "(292, 'P.FIGUEIRA-FREE WAY', 1),"+//**************************************
                "(295, 'VILA ELZA-ANCHIETA', 102),"+
                "(296, 'P.FIGUEIRA-PUC', 1),"+//**************************************
                "(297, 'P.FIGUEIRA-IGUATEMI', 63),"+
                "(298, 'P.FIGUEIRA-VIDA NOVA', 68),"+
                "(300, 'DISTRITO INDUSTRIAL CACHOEIRINHA', 19),"+
                "(302, 'MARINGA', 57),"+
                "(303, 'TAIMBÉ', 89),"+
                "(304, 'TAIMBÉ-FORMOSA-MARINGA', 1),"+//**************************************
                "(305, 'P.FIGUEIRA-P.ALVES', 1),"+//**************************************
                "(306, 'P.FIGUEIRA', 59),"+
                "(307, 'P.FIGUEIRA-CAIRU', 1),"+//**************************************
                "(309, 'P.FIGUEIRA-F.FERRARI', 62),"+
                "(311, 'SÃO PEDRO-CEASA', 77),"+
                "(312, 'CABRAL', 18),"+
                "(313, 'UMBU', 96),"+
                "(314, 'SÃO PEDRO', 1),"+//**************************************
                "(315, 'FERNANDO FERRARI', 32),"+
                "(317, 'UMBU-CAIRU', 1),"+//**************************************
                "(318, 'UMBU-SERTÓRIO', 1),"+//**************************************
                "(319, 'SALOMÉ', 71),"+
                "(320, 'SALOMÉ-CAIRU', 1),"+//**************************************
                "(321, 'SALOMÉ-SERTÓRIO', 1),"+//**************************************
                "(322, 'P.FIGUEIRA-SÃO PEDRO', 66),"+
                "(323, 'VILA ELZA', 99),"+
                "(324, 'UMBU-ANCHIETA-AMERICANA', 103),"+
                "(326, 'VILA ELZA-SERTÓRIO', 1),"+//**************************************
                "(327, 'VILA ELZA-CAIRU', 1),"+//**************************************
                "(328, 'SUMARÉ', 87),"+
                "(329, 'ALGARVE', 51),"+
                "(330, 'ALGARVE CAIRU', 1),"+//**************************************
                "(331, 'ALGARVE SERTORIO', 1),"+//**************************************
                "(333, 'ALGARVE J.POA', 1),"+//**************************************
                "(334, 'J.PORTO ALEGRE', 52),"+
                "(335, 'J.POA-CAIRU', 1),"+//**************************************
                "(336, 'J.POA-SERTÓRIO', 1),"+//**************************************
                "(338, 'J.APARECIDA-PDA 40', 1),"+//**************************************
                "(339, 'J.APARECIDA-PDA 40', 1),"+//**************************************
                "(344, 'SÃO PEDRO-PDA', 79),"+
                "(345, 'SÃO PEDRO-PDA', 79),"+
                "(346, 'J.APARECIDA', 41),"+
                "(347, 'J.APARECIDA-CAIRU', 45),"+
                "(348, 'J.APARECIDA-SERTÓRIO', 1),"+//**************************************
                "(349, 'J.APARECIDA-PROTÁSIO ALVES', 44),"+
                "(350, 'ALVORADA', 14),"+
                "(351, '11 DE ABRIL', 58),"+
                "(352, 'UMBU-P.ALVES', 1),"+//**************************************
                "(353, 'STELLA MARIS-J.APARECIDA', 1),"+//**************************************
                "(355, 'ESTANCIA GRANDE', 29),"+
                "(356, 'ESTANCIA GRANDE P.40', 31),"+
                "(357, 'VILA ELZA-BIG', 1),"+//**************************************
                "(358, 'VILA ELZA-BIG', 1),"+//**************************************
                "(359, 'ESTANCIA GRANDE CAIRU', 1),"+//**************************************
                "(360, 'SALOMÉ-ANCHIETA', 72),"+
                "(361, 'ALVORADA VIAMAO', 3),"+
                "(362, 'STELLA MARIS', 86),"+
                "(363, 'INTERSUL', 37),"+
                "(364, 'DISTRITO INDUSTRIAL BALTAZAR', 24),"+
                "(365, 'DISTRITO INDUSTRIAL P.40', 1),"+//**************************************
                "(366, 'PINDORAMA', 1),"+//**************************************
                "(368, 'SANTA CLARA', 76),"+
                "(369, 'ALVORADA INTERSUL', 37),"+
                "(370, 'P.FIGUEIRA-VIDA NOVA-FAIXA', 65),"+
                "(371, 'VILA ELZA-AMERICANA', 20),"+
                "(372, 'VILA ELZA-BIG-AMERICANA', 1),"+//**************************************
                "(373, 'ALGARVE FREEWAY', 1),"+//**************************************
                "(374, 'VILA ELZA-BIG-AMERICANA', 1),"+//**************************************
                "(375, 'PINDORAMA', 1),"+//**************************************
                "(376, 'STELLA MARIS-J.APARECIDA-CAIRU', 1),"+//**************************************
                "(377, 'STELLA MARIS-BIG', 81),"+
                "(378, 'STELLA MARIS-BIG', 81),"+
                "(380, 'P.FIGUEIRA-IPIRANGA', 1),"+//**************************************
                "(381, 'PORTO VERDE', 70),"+
                "(382, 'AMERICANA ANCHIETA', 1),"+//**************************************
                "(383, 'VILA ELZA-FREE WAY', 1),"+//**************************************
                "(384, 'VILA ELZA-PROTÁSIO ALVES', 1),"+//**************************************
                "(385, 'VILA ELZA-ZIVI', 1),"+//**************************************
                "(386, 'VILA ELZA-ZIVI', 1),"+//**************************************
                "(387, 'VILA ELZA-IPIRANGA', 1),"+//**************************************
                "(390, 'J.APARECIDA-BH', 43),"+
                "(391, 'J.APARECIDA-P.ALVES-BH', 44),"+
                "(392, 'J.APARECIDA-IGUATEMI', 46),"+
                "(393, 'ALVORADA INTERSUL B.H', 1),"+//**************************************
                "(394, 'VILA ELZA-PIRATINI-UMBU-AMERICANA', 1),"+//**************************************
                "(396, 'AMERICANA CEE', 16),"+
                "(400, 'P.FIGUEIRA-PUC', 1),"+//**************************************
                "(401, 'ALVORADA', 14),"+
                "(402, 'J.APARECIDA', 41),"+
                "(403, 'ALVORADA CAIRU', 1),"+//**************************************
                "(404, 'ALVORADA SERTORIO', 1),"+
                "(406, 'J.APARECIDA-CAIRU', 1),"+//**************************************
                "(407, 'SANTA CLARA', 76),"+
                "(408, 'MARINGA', 57),"+
                "(409, 'TAIMBÉ', 89),"+
                "(410, 'FORMOSA', 35),"+
                "(411, 'J.APARECIDA-SERTÓRIO', 1),"+//**************************************
                "(412, 'J.APARECIDA-PROTÁSIO ALVES', 1),"+//**************************************
                "(413, 'ALVORADA', 14),"+
                "(414, '11 DE ABRIL', 58),"+
                "(415, 'ALVORADA FREEWAY', 1),"+//**************************************
                "(416, 'ALVORADA EXECUTIVO ASSIS BRASIL', 1),"+//**************************************
                "(417, 'UMBU-P.ALVES', 1),"+//**************************************
                "(420, 'ALVORADA INTERSUL', 37),"+
                "(421, 'ALVORADA PUC', 1),"+//**************************************
                "(422, 'STELLA MARIS-J.APARECIDA', 1),"+//**************************************
                "(423, 'STELLA MARIS-J.APARECIDA-CAIRU', 1),"+
                "(424, 'P.FIGUEIRA-IPIRANGA', 1),"+
                "(425, 'P.FIGUEIRA-P.ALVES', 1),"+
                "(426, 'ESTANCIA GRANDE', 1),"+
                "(427, 'DISTRITO INDUSTRIAL BALTAZAR', 1),"+
                "(428, 'INTERSUL', 1),"+
                "(429, 'ESTANCIA GRANDE CAIRU', 1),"+//**************************************
                "(430, 'P.FIGUEIRA', 59),"+
                "(431, 'P.FIGUEIRA-F.FERRARI', 62),"+
                "(432, 'P.FIGUEIRA-CAIRU', 1),"+//**************************************
                "(433, 'UMBU-ANCHIETA-AMERICANA', 97),"+
                "(434, 'P.FIGUEIRA-F.FERRARI', 62),"+
                "(436, 'SÃO PEDRO-CEASA', 77),"+
                "(437, 'STELLA MARIS', 86),"+
                "(438, 'CABRAL', 18),"+
                "(439, 'UMBU', 96),"+
                "(440, 'SÃO PEDRO', 1),"+
                "(441, 'FERNANDO FERRARI', 1),"+
                "(443, 'UMBU-CAIRU', 1),"+//**************************************
                "(444, 'UMBU-SERTÓRIO', 1),"+//**************************************
                "(445, 'SALOMÉ', 71),"+
                "(446, 'SALOMÉ-PDA40', 79),"+
                "(447, 'SALOMÉ-SERTÓRIO', 1),"+//**************************************
                "(448, 'P.FIGUEIRA-SÃO PEDRO', 66),"+
                "(449, 'VILA ELZA', 99),"+
                "(450, 'SALOMÉ-ANCHIETA', 72),"+
                "(452, 'VILA ELZA-SERTÓRIO', 1),"+//**************************************
                "(453, 'VILA ELZA-CAIRU', 1),"+//**************************************
                "(454, 'AMERICANA', 15),"+
                "(455, 'AMERICANA CAIRU', 1),"+//**************************************
                "(456, 'SUMARÉ', 87),"+
                "(458, 'AMERICANA ANCHIETA', 1),"+//**************************************
                "(459, 'ALGARVE', 51),"+
                "(460, 'ALGARVE CAIRU', 1),"+//**************************************
                "(461, 'ALGARVE SERTORIO', 1),"+//**************************************
                "(464, 'J.PORTO ALEGRE', 52),"+
                "(465, 'J.POA-CAIRU', 1),"+//**************************************
                "(466, 'J.POA-SERTÓRIO', 1),"+//**************************************
                "(470, 'ALVORADA FREEWAY', 1),"+//**************************************
                "(471, 'AMERICANA CEE FREEWAY', 1),"+//**************************************
                "(472, 'ALGARVE FREEWAY', 1),"+//**************************************
                "(473, 'ALGARVE EXEC. ASSIS BRASIL', 1),"+//**************************************
                "(474, 'VILA ELZA-FREE WAY', 1),"+//**************************************
                "(475, 'P.FIGUEIRA-VIDA NOVA-FAIXA', 68),"+
                "(477, 'PORTO VERDE', 70),"+
                "(479, 'VILA ELZA-PROTÁSIO ALVES', 1),"+//**************************************
                "(480, 'VILA ELZA-IPIRANGA', 1),"+//**************************************
                "(481, 'J.APARECIDA-BH', 43),"+
                "(482, 'ALVORADA INTERSUL B.H', 1),"+//**************************************
                "(483, 'ALGARVE J.POA', 1),"+//**************************************
                "(485, 'P.FIGUEIRA-IGUATEMI', 63),"+
                "(486, 'J.APARECIDA-P.ALVES-BH', 44),"+
                "(488, 'J.APARECIDA-IGUATEMI', 46),"+
                "(489, 'P.FIGUEIRA-VIDA NOVA', 68),"+
                "(490, 'MARINGA-FORMOSA', 1),"+//**************************************
                "(491, 'ALVORADA ANCHIETA', 1),"+//**************************************
                "(493, 'VILA ELZA-ANCHIETA', 102),"+
                "(496, 'P.FIGUEIRA-SÃO PEDRO-F.FERRARI', 1),"+//**************************************
                "(497, 'P.FIGUEIRA-FREE WAY', 1),"+//**************************************
                "(500, 'ALGARVE ANCHIETA', 1),"+//**************************************
                "(501, 'J.POA-ANCHIETA', 1),"+//**************************************
                "(502, 'PORTO VERDE-SERTÓRIO', 1),"+//**************************************
                "(504, 'P.FIGUEIRA-NOVA ALVORADA', 64),"+
                "(505, 'VILA ELZA-P.ALVES-SÃO PEDRO', 1),"+//**************************************
                "(508, 'STELLA MARIS-SERTÓTIO', 84),"+
                "(509, 'VILA ELZA-PUC', 1),"+//**************************************
                "(511, 'SALOMÉ-PROTÁSIO ALVES', 1),"+//**************************************
                "(513, 'VILA ELZA-IPIRANGA-SÃO PEDRO', 1),"+//**************************************
                "(514, 'AMERICANA FREEWAY', 1),"+//**************************************
                "(515, 'UMBU-IPIRANGA', 1),"+//**************************************
                "(516, 'ALVORADA IPIRANGA', 1),"+//**************************************
                "(521, 'TM5-VILA ELZA', 92),"+
                "(522, 'UMBU-CAIRU-SERTÓRIO', 1),"+//**************************************
                "(523, 'UMBU-ANCHIETA-BALTAZAR', 1),"+
                "(525, 'PORTO VERDE-JALGARVE-JPOA', 1),"+
                "(526, 'VILA ELZA-SERTÓRIO-CAIRU', 97),"+
                "(527, 'PORTO VERDE-SERTÓRIO-CAIRU', 1),"+
                "(531, 'J.APARECIDA-SERVIDÃO', 1),"+//**************************************
                "(535, 'ALVORADA PUC', 1),"+//**************************************
                "(538, 'ALGARVE J.POA PORTO VERDE CAIRU', 1),"+//**************************************
                "(539, 'STELLA MARIS-SERTÓRIO-CAIRU', 84),"+
                "(540, 'SÃO PEDRO -RUI RAMOS', 1),"+//**************************************
                "(541, 'J.APARECIDA-IPIRANGA', 48),"+
                "(549, 'SÃO PEDRO-FERRARI-PROTÁSIO ALVES', 1),"+//**************************************
                "(710, 'TM5', 93),"+
                "(713, 'TM5-VILA ELZA', 1),"+//**************************************
                "(951, 'TM5', 93),"+
                "(952, 'TM5', 93),"+
                "(953, 'TM1', 92),"+
                "(954, 'TM1', 1),"+//**************************************
                "(955, 'TM2', 1),"+//**************************************
                "(7199, 'ESTANCIA GRANDE MICRO', 1);"//**************************************
        );

        db.execSQL(
                "CREATE TABLE if not EXISTS d_execoes " +
                        "(_id INTEGER PRIMARY KEY," +
                        "nome VARCHAR (100)," +
                        "tipo_execao INTEGER(11));"
        );

        db.execSQL( "INSERT INTO `d_execoes` (`_id`, `nome`, `tipo_execao`) VALUES" +
                "(1111, 'Clicano Oculos', 0),"+
                "(2222, 'Oculos Escuros', 0),"+
                "(3333, 'Tio do Oculos', 0),"+
                "(4185, 'João Fulano', 1),"+
                "(5261, 'Fulano Cavalo', 1),"+
                "(5265, 'Nome Sobrenome', 1),"+
                "(5523, 'Nome Sobrenome', 1),"+
                "(5736, 'Nome Sobrenome', 1),"+
                "(5981, 'Nome Sobrenome', 1),"+
                "(6363, 'Nome Sobrenome', 1),"+
                "(6511, 'Nome Sobrenome', 1),"+
                "(6591, 'Nome Sobrenome', 1),"+
                "(7015, 'Nome Sobrenome', 1),"+
                "(7639, 'Nome Sobrenome', 1),"+
                "(7696, 'Nome Sobrenome', 1),"+
                "(7817, 'Nome Sobrenome', 1),"+
                "(7836, 'Nome Sobrenome', 1),"+
                "(7878, 'Nome Sobrenome', 1),"+
                "(4375, 'Nome Sobrenome', 2),"+
                "(6956, 'Nome Sobrenome', 2),"+
                "(7897, 'Nome Sobrenome', 2),"+
                "(26864, 'Nome Sobrenome', 2);"
        );

        db.execSQL(
                "CREATE TABLE if not EXISTS tipo_carro " +
                        "(_id INTEGER PRIMARY KEY," +
                        "tipoCarro INTEGER (11)," +
                        "d_adaptado INTEGER(11));"
        );

        db.execSQL( "INSERT INTO `tipo_carro` (`_id`, `tipoCarro`, `d_adaptado`) VALUES" +
                "(1111, 0, 0),"+
                "(2222, 1, 0),"+
                "(3333, 2, 0),"+
                "(4444, 3, 0),"+
                "(2603, 0, 1),"+
                "(2613, 0, 1),"+
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
                "(7717, 2, 1);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table nome_linha;");
        onCreate(db);
    }
}