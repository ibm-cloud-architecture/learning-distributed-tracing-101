FROM node:12.9.1

# Create app directory
WORKDIR /usr/src/app

COPY package.json .

RUN npm install
# If you are building your code for production
# RUN npm ci --only=production

# Bundle app source
COPY . .

EXPOSE 8081
CMD [ "npm", "start" ]