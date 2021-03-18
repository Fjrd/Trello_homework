package org.levelup.trello.service.hibernate;

import org.hibernate.SessionFactory;
import org.levelup.trello.model.Board;
import org.levelup.trello.model.BoardColumn;
import org.levelup.trello.model.User;
import org.levelup.trello.service.BoardRepository;

public class HibernateBoardRepository extends AbstractHibernateRepository implements BoardRepository {

    public HibernateBoardRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public Board createBoard(Integer userId, String name, boolean favourite) {
//        Comparator<Integer> i = new Comparator<Integer>() { // анонимный внутренний класс
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return 0;
//            }
//        };
//        // i.compare(1, 2);
//        new ArrayList<Integer>() {
//            @Override
//            public boolean add(Integer integer) {
//                super.add(integer);
//                return false;
//            }
//
//            @Override
//            protected void removeRange(int fromIndex, int toIndex) {
//                super.removeRange(fromIndex, toIndex);
//            }
//        };
        return runWithTransaction(session -> {
            Board board = new Board();
            board.setName(name);
            board.setFavourite(favourite);
            board.setOwner(session.load(User.class, userId));

            session.persist(board);
            // session.flush(); session.clear(); <-- Batch update/insert
            return board;
        });
    }

    public Board addColumn(Integer boardId, String name, int columnOrder) {
        return runWithTransaction(session -> {
            BoardColumn column = new BoardColumn();
            column.setName(name);
            column.setOrder(columnOrder);

            Board board = session.get(Board.class, boardId); // board - persistent/managed
            board.getColumns().add(column); // insert into columns values (name, order, board.getId())

            return board;
        });
    }

}
