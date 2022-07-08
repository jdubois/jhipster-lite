import { HttpResponseInterceptor, RouteMatcher, StaticResponse } from '../../../../../node_modules/cypress/types/net-stubbing';

type ResponseSender = {
  send: () => void;
};

export const interceptForever = (requestMatcher: RouteMatcher, response?: StaticResponse | HttpResponseInterceptor): ResponseSender => {
  let send;

  const trigger = new Promise(resolve => {
    send = resolve;
  });

  cy.intercept(requestMatcher, request => {
    return trigger.then(() => {
      request.reply(response);
    });
  });

  return { send };
};
