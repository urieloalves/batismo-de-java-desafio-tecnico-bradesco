package database.dao;

import database.model.TB_REPLICACAO_PROCESSO;
import database.model.TB_REPLICACAO_PROCESSO_TABELA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProcessoTabelaDAO {

    private Connection conn;

    private static final String SQL_SELECT_BY_PROCESSO_HABILITADO =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA WHERE processo_id = ? AND habilitado = TRUE AND ORDER BY order";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA";

    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA WHERE id = ?";

    private static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_PROCESSO_TABELA (processo_id, tabela_origem, tabela_destino, ordem, habilitado, ds_where) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE TB_REPLICACAO_PROCESSO_TABELA SET processo_id = ?, tabela_origem = ?, tabela_destino = ?, ordem = ?, ds_where = ? WHERE id = ?";

    private static final String SQL_DELETE =
            "DELETE FROM TB_REPLICACAO_PROCESSO_TABELA WHERE id = ?";

    private PreparedStatement pstSelectByProcessoHabilitado;
    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectById;
    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDelete;

    public ProcessoTabelaDAO(Connection conn) throws SQLException {
        this.conn = conn;
        this.pstSelectByProcessoHabilitado = conn.prepareStatement(SQL_SELECT_BY_PROCESSO_HABILITADO);
        this.pstSelectAll = conn.prepareStatement(SQL_SELECT_ALL);
        this.pstSelectById = conn.prepareStatement(SQL_SELECT_BY_ID);
        this.pstInsert = conn.prepareStatement(SQL_INSERT);
        this.pstUpdate = conn.prepareStatement(SQL_UPDATE);
        this.pstDelete = conn.prepareStatement(SQL_DELETE);
    }

   public ArrayList<TB_REPLICACAO_PROCESSO_TABELA> selectByProcessoHabilitado(Long id) throws SQLException {
        ArrayList<TB_REPLICACAO_PROCESSO_TABELA> list = new ArrayList<>();
        pstSelectByProcessoHabilitado.setLong(1, id);
        try (ResultSet rs = pstSelectByProcessoHabilitado.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
   }

   public ArrayList<TB_REPLICACAO_PROCESSO_TABELA> selectAll() throws SQLException {
        ArrayList<TB_REPLICACAO_PROCESSO_TABELA> list = new ArrayList<>();
       try (ResultSet rs = pstSelectAll.executeQuery()) {
           while (rs.next()) {
               list.add(map(rs));
           }
       }
       return list;
   }

    public TB_REPLICACAO_PROCESSO_TABELA selectById(Long id) throws SQLException {
        pstSelectById.setLong(1, id);
        try(ResultSet rs = pstSelectById.executeQuery()) {
            return rs.next() ? map(rs) : null;
        }
    }

   public void insert(TB_REPLICACAO_PROCESSO_TABELA tb) throws SQLException {
        pstInsert.setLong(1, tb.getProcesso_id());
        pstInsert.setString(2, tb.getTabela_origem());
        pstInsert.setString(3, tb.getTabela_destino());
        pstInsert.setInt(4, tb.getOrdem());
        pstInsert.setBoolean(5,tb.getHabilitado());
        pstInsert.setString(6, tb.getDs_where());
        pstInsert.executeUpdate();
   }

   public void update(TB_REPLICACAO_PROCESSO_TABELA tb) throws SQLException {
       pstUpdate.setString(1, tb.getTabela_origem());
       pstUpdate.setString(2, tb.getTabela_destino());
       pstUpdate.setInt(3, tb.getOrdem());
       pstUpdate.setBoolean(4,tb.getHabilitado());
       pstUpdate.setString(5, tb.getDs_where());
       pstUpdate.setLong(6, tb.getId());
       pstUpdate.executeUpdate();
   }

   public void delete(TB_REPLICACAO_PROCESSO_TABELA tb) throws SQLException {
        pstDelete.setLong(1, tb.getId());
        pstDelete.executeUpdate();
   }

   private TB_REPLICACAO_PROCESSO_TABELA map(ResultSet rs) throws SQLException {
       TB_REPLICACAO_PROCESSO_TABELA tb = new TB_REPLICACAO_PROCESSO_TABELA();
       tb.setId(rs.getLong("id"));
       tb.setProcesso_id(rs.getLong("processo_id"));
       tb.setTabela_origem(rs.getString("tabela_origem"));
       tb.setTabela_destino(rs.getString("tabela_destino"));
       tb.setOrdem(rs.getInt("ordem"));
       tb.setHabilitado(rs.getBoolean("habilitado"));
       tb.setDs_where(rs.getString("ds_where"));
       return tb;
   }
}
