# TSDetect Dashboard

The dashboard provides users with high-level data collected from the TSDetect tool in a friendly view utilizing tables, graphs and various filtering options for said data.

## Dependencies

- PNPM (v7.0+): https://pnpm.io/installation
- NodeJS (v16.17.1+): https://nodejs.org/en/

## Running Locally (Dev Server)

Provided terminal commands should be ran within the subdirectory labeled `dashboard`. If commands are ran at the top level of the project, the commands will not work correctly.

### Install Project Dependencies
Ensure that you have installed NodeJS & pnpm correctly using the following commands:
```
node -v
pnpm -v
```
If installed correctly, each should provide a version number.

Once installed correctly, install the application dependencies using the following command:
```
pnpm install
```

### Running Dev Server
To start up the local development server, run command:
```
pnpm dev
```
You will be redirected to the dashboard and all changes made to the source code will be shown in 'realtime' through webpack's use of Hot Module Reloading.
