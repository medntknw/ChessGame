import React, { useState } from 'react';

export default function Welcome({ onStartGame }) {
  const [player1, setPlayer1] = useState('');
  const [player2, setPlayer2] = useState('');

  const handleStart = () => {
    if (player1.trim() && player2.trim()) {
      onStartGame(player1, player2);
    } else {
      alert('Please enter names for both players!');
    }
  };

  return (
    <div className="min-h-screen bg-gray-900 flex items-center justify-center">
      <div className="bg-gray-800 rounded-lg shadow-lg p-6 w-96">
        <h1 className="text-3xl font-bold text-white text-center mb-4">
          Multiplayer Chess
        </h1>
        <div className="space-y-4">
          <div>
            <label className="block text-gray-300 text-sm mb-2" htmlFor="player1">
              Player 1 Name
            </label>
            <input
              type="text"
              id="player1"
              value={player1}
              onChange={(e) => setPlayer1(e.target.value)}
              placeholder="Enter Player 1 Name"
              className="w-full px-4 py-2 rounded-md text-gray-900 focus:ring-2 focus:ring-indigo-400 outline-none"
            />
          </div>
          <div>
            <label className="block text-gray-300 text-sm mb-2" htmlFor="player2">
              Player 2 Name
            </label>
            <input
              type="text"
              id="player2"
              value={player2}
              onChange={(e) => setPlayer2(e.target.value)}
              placeholder="Enter Player 2 Name"
              className="w-full px-4 py-2 rounded-md text-gray-900 focus:ring-2 focus:ring-indigo-400 outline-none"
            />
          </div>
          <button
            onClick={handleStart}
            className="w-full bg-indigo-500 text-white py-2 rounded-md hover:bg-indigo-600 transition"
          >
            Start Game
          </button>
        </div>
      </div>
    </div>
  );
}
