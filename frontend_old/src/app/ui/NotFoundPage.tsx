import * as React from 'react';

import { RouteComponentProps } from 'react-router-dom';

type NotFoundPageProps = RouteComponentProps<{}>;

const NotFoundPage = ({ location }: NotFoundPageProps) =>
  <section>
    <h1>Not found</h1>
    <p>
      We're sorry, but the requested page <b>{`${location.pathname}`}</b> has
      not been found.
    </p>
  </section>;

export default NotFoundPage;
