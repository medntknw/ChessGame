'use client'
import React, { useState, useEffect } from 'react';

const ChessBoard = () => {
  const [selectedBox, setSelectedBox] = useState(null);
  const [board, setBoard] = useState(Array(8).fill().map(() => Array(8).fill(null)));
  const [currentPlayer, setCurrentPlayer] = useState('white');

  useEffect(() => {
    fetchBoardState();
  }, []);

  const fetchBoardState = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/chess/board');
      const data = await response.json();
      updateBoardFromState(data);
    } catch (error) {
      alert("Failed to fetch board state");
    }
  };
  
  const updateBoardFromState = (boardState) => {
    const newBoard = Array(8).fill().map(() => Array(8).fill(null));
    
    for (let i = 0; i < 8; i++) {
      for (let j = 0; j < 8; j++) {
        const piece = boardState.squares[i][j];
        if (piece) {
          newBoard[i][j] = {
            piece: getPieceSymbol(piece.type, piece.isWhite),
            color: piece.isWhite ? 'white' : 'black'
          };
        }
      }
    }
    setBoard(newBoard);
  };

  const getPieceSymbol = (type, isWhite) => {
    const pieces = {
      'KING': isWhite ? '♔' : '♚',
      'QUEEN': isWhite ? '♕' : '♛',
      'ROOK': isWhite ? '♖' : '♜',
      'BISHOP': isWhite ? '♗' : '♝',
      'KNIGHT': isWhite ? '♘' : '♞',
      'PAWN': isWhite ? '♙' : '♟'
    };
    return pieces[type];
  };

  const handleBoxClick = async (row, col) => {
    if (!selectedBox) {
      if (board[row][col]?.color === currentPlayer) {
        setSelectedBox({ row, col });
      }
    } else {
      try {
        const response = await fetch('http://localhost:8080/api/chess/move', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            startX: selectedBox.row,
            startY: selectedBox.col,
            endX: row,
            endY: col
          })
        });

        const result = await response.json();
        
        if (result.success) {
          updateBoardFromState(result.boardState);
          setCurrentPlayer(currentPlayer === 'white' ? 'black' : 'white');
          
          if (result.message === "Game Over") {
            alert(`Game Over! ${currentPlayer.toUpperCase()} wins!`);
            
          }
        } else {
          alert("Invalid move!");
        }
      } catch (error) {
        alert("Failed to make move");
      }
      setSelectedBox(null);
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <div style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(8, 48px)',
        border: '1px solid #ccc',
        width: 'fit-content',
        margin: '0 auto'
      }}>
        {board.map((row, rowIndex) => (
          row.map((box, colIndex) => {
            const isSelected = selectedBox?.row === rowIndex && selectedBox?.col === colIndex;
            const isDark = (rowIndex + colIndex) % 2 === 1;
            
            return (
              <div
                key={`${rowIndex}-${colIndex}`}
                style={{
                  width: '48px',
                  height: '48px',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  fontSize: '32px',
                  cursor: 'pointer',
                  backgroundColor: isDark ? '#B0B0B0' : '#F0F0F0',
                  border: isSelected ? '2px solid blue' : 'none',
                  color: box?.color === 'black' ? 'black' : '#666'
                }}
                onClick={() => handleBoxClick(rowIndex, colIndex)}
              >
                {box?.piece}
              </div>
            );
          })
        ))}
      </div>
      <div style={{ textAlign: 'center', marginTop: '20px' }}>
        Current Player: {currentPlayer}
      </div>
    </div>
  );
};

export default ChessBoard;