import { render, fireEvent, act } from '@testing-library/react';
import { describe, it, expect } from 'vitest';

import LoginModal from '@/login/primary/loginModal';

const LoginModalRender = (open: boolean) => render(<LoginModal open={open} onClose={() => {}} />);

describe('test login modal', () => {
  it('describe should render without crashing', () => {
    LoginModalRender(true);
  });

  it('should contain login title', () => {
    const { getByText } = LoginModalRender(true);
    const title = getByText('Connect');
    expect(title).toBeTruthy();
  });

  it('should be closed', () => {
    const { queryByText } = LoginModalRender(false);
    const title = queryByText('Connect');
    expect(title).toBeFalsy();
  });

  it('should contain username input', () => {
    const { getByPlaceholderText } = LoginModalRender(true);
    const username = getByPlaceholderText("Nom d'utilisateur");
    expect(username).toBeTruthy();
  });

  it('should contain password input', () => {
    const { getByPlaceholderText } = LoginModalRender(true);
    const username = getByPlaceholderText('Mot de passe');
    expect(username).toBeTruthy();
  });

  it('should contain submit button', () => {
    const { getByTestId } = LoginModalRender(true);
    const submit = getByTestId('submit-button');
    expect(submit).toBeTruthy();
  });

  it('render the modal on login button click and close the modal on submit button click', () => {
    const { getByLabelText, getByTestId } = LoginModalRender(true);
    act(() => {
      fireEvent.change(getByLabelText("Nom d'utilisateur"), {
        target: { value: 'admin' },
      });
      fireEvent.change(getByLabelText('Mot de passe'), {
        target: { value: 'admin' },
      });
      const submitButton = getByTestId('submit-button');
      fireEvent.click(submitButton);
    });
  });

  it('should contain error message when submit button is clicked with empty value', async () => {
    const { getByTestId } = LoginModalRender(true);
    await act(async () => {
      const submitButton = getByTestId('submit-button');
      fireEvent.click(submitButton);
    });
    expect(getByTestId('error-message')).toBeTruthy();
  });
});
