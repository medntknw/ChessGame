'use client';
import React, { useState } from 'react';
import Welcome from '@/components/Welcome';
import ChessBoard from '@/components/ChessBoard';

export default function HomePage() {
  const [player1, setPlayer1] = useState('');
  const [player2, setPlayer2] = useState('');
  const [gameId, setGameId] = useState('');
  const [error, setError] = useState('');
  const URL = 'http://localhost:8080/api/chess';

  const createGame = async (player1Name, player2Name, newGameId) => {
    try {
      const response = await fetch(`${URL}/game`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          player1: player1Name,
          player2: player2Name,
          gameId: newGameId
        })
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Failed to create game');
      }

      const data = await response.json();
      return data;
    } catch (error) {
      setError(`Failed to create game: ${error.message}`);
      return null;
    }
  };

  const startGame = async (player1Name, player2Name) => {
    setError('');
    const newGameId = `game-${Date.now()}`;
    
    const gameData = await createGame(player1Name, player2Name, newGameId);
    if (gameData) {
      setPlayer1(player1Name);
      setPlayer2(player2Name);
      setGameId(newGameId);
    } else {
      setPlayer1('');
      setPlayer2('');
      setGameId('');
    }
  };

  const endGame = () => {
    setPlayer1('');
    setPlayer2('');
    setGameId('');
    setError('');
  };

  return (
    <div className="container mx-auto px-4 py-8">
      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}
      
      {player1 && player2 && gameId ? (
        <div>
          <div className="text-center mb-4">
            <h2 className="text-2xl font-bold">Chess Game</h2>
            <p className="text-gray-600">
              {player1} (White) vs {player2} (Black)
            </p>
          </div>
          <ChessBoard gameId={gameId} endGame={endGame} />
        </div>
      ) : (
        <Welcome onStartGame={startGame} />
      )}
    </div>
  );
}