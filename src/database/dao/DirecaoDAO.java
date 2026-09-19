package database.dao;

import database.model.TB_REPLICACAO_DIRECAO;
import database.model.TB_REPLICACAO_PROCESSO_TABELA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DirecaoDAO {

    private Connection conn;

    private static final String SQL_SELECT_BY_PROCESSO_HABILITADO =
            "SELECT * FROM TB_REPLICACAO_DIRECAO WHERE processo_id = ? AND habilitado = TRUE";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM TB_REPLICACAO_DIRECAO";

    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM TB_REPLICACAO_DIRECAO WHERE id = ?";

    private static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_DIRECAO (processo_id, direcao_origem, direcao_destino, usuario_origem, usuario_destino, senha_origem, senha_destino, habilitado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE TB_REPLICACAO_DIRECAO SET processo_id = ?, direcao_origem = ?, direcao_destino = ?, usuario_origem = ?, usuario_destino = ?, senha_origem = ?, senha_destino = ?, habilitado = ? WHERE id = ?";

    private static final String SQL_DELETE =
            "DELETE FROM TB_REPLICACAO_DIRECAO WHERE id = ?";

    private PreparedStatement pstSelectByProcessoHabilitado;
    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectById;
    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDelete;

    public DirecaoDAO(Connection conn) throws SQLException {
        this.conn = conn;
        this.pstSelectByProcessoHabilitado = conn.prepareStatement(SQL_SELECT_BY_PROCESSO_HABILITADO);
        this.pstSelectAll = conn.prepareStatement(SQL_SELECT_ALL);
        this.pstSelectById = conn.prepareStatement(SQL_SELECT_BY_ID);
        this.pstInsert = conn.prepareStatement(SQL_INSERT);
        this.pstUpdate = conn.prepareStatement(SQL_UPDATE);
        this.pstDelete = conn.prepareStatement(SQL_DELETE);
    }

    private ArrayList<TB_REPLICACAO_DIRECAO> selectByProcessoHabilitado(Long processo_id) throws SQLException {
        ArrayList<TB_REPLICACAO_DIRECAO> list = new ArrayList<>();
        pstSelectByProcessoHabilitado.setLong(1, processo_id);
        try (ResultSet rs = pstSelectByProcessoHabilitado.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    private ArrayList<TB_REPLICACAO_DIRECAO> selectAll() throws SQLException {
        ArrayList<TB_REPLICACAO_DIRECAO> list = new ArrayList<>();
        try (ResultSet rs = pstSelectAll.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public TB_REPLICACAO_DIRECAO selectById(Long id) throws SQLException {
        pstSelectById.setLong(1, id);
        try(ResultSet rs = pstSelectById.executeQuery()) {
            return rs.next() ? map(rs) : null;
        }
    }

    public void insert(TB_REPLICACAO_DIRECAO tb) throws SQLException {
        pstInsert.setLong(1, tb.getProcesso_id());
        pstInsert.setString(2, tb.getDirecao_origem());
        pstInsert.setString(3, tb.getDirecao_destino());
        pstInsert.setString(4, tb.getUsuario_origem());
        pstInsert.setString(5, tb.getUsuario_destino());
        pstInsert.setString(6, tb.getSenha_origem());
        pstInsert.setString(7, tb.getSenha_destino());
        pstInsert.setBoolean(8, tb.getHabilitado());
        pstInsert.executeUpdate();
    }

    public void update(TB_REPLICACAO_DIRECAO tb) throws SQLException {
        pstUpdate.setLong(1, tb.getProcesso_id());
        pstUpdate.setString(2, tb.getDirecao_origem());
        pstUpdate.setString(3, tb.getDirecao_destino());
        pstUpdate.setString(4, tb.getUsuario_origem());
        pstUpdate.setString(5, tb.getUsuario_destino());
        pstUpdate.setString(6, tb.getSenha_origem());
        pstUpdate.setString(7, tb.getSenha_destino());
        pstUpdate.setBoolean(8, tb.getHabilitado());
        pstUpdate.setLong(9, tb.getId());
        pstUpdate.executeUpdate();
    }

    public void deleteById(Long id) throws SQLException {
        pstDelete.setLong(1, id);
        pstDelete.executeUpdate();
    }

    private TB_REPLICACAO_DIRECAO map(ResultSet rs) throws SQLException {
        TB_REPLICACAO_DIRECAO tb = new TB_REPLICACAO_DIRECAO();
        tb.setId(rs.getLong("id"));
        tb.setProcesso_id(rs.getLong("processo_id"));
        tb.setDirecao_origem(rs.getString("direcao_origem"));
        tb.setDirecao_destino(rs.getString("direcao_destino"));
        tb.setUsuario_origem(rs.getString("usuario_origem"));
        tb.setUsuario_destino(rs.getString("usuario_destino"));
        tb.setSenha_origem(rs.getString("senha_origem"));
        tb.setSenha_destino(rs.getString("senha_destino"));
        tb.setHabilitado(rs.getBoolean("habilitado"));
        return tb;
    }
}
