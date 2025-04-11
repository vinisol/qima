module.exports = {
  preset: "jest-preset-angular",
  setupFilesAfterEnv: ["<rootDir>/setup-jest.ts"],
  testMatch: ["**/+(*.)+(spec).+(ts)"],
  transform: {
    "^.+\\.(ts|js|html)$": "jest-preset-angular",
  },
  globals: {
    "ts-jest": {
      tsconfig: "<rootDir>/tsconfig.spec.json",
      stringifyContentPathRegex: "\\.html$",
      useESM: true,
    },
  },
  moduleFileExtensions: ["ts", "html", "js", "json"],
  coverageDirectory: "coverage",
  collectCoverageFrom: ["src/**/*.ts", "!src/main.ts"],
};
