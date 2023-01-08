import { useCallback, useContext, useState } from 'react';
import { useForm } from 'react-hook-form';
import { Button, Input, Modal, Spacer, Text } from '@nextui-org/react';

import { login } from '@/login/services/login';
import { UserInfoContext } from '@/login/primary/loginForm';

import './LoginModal.scss';

const LoginModal = ({ open, onClose }: LoginModalType) => {
  const { register, handleSubmit } = useForm();
  const [error, setError] = useState<boolean>(false);
  const { setUsername, setToken } = useContext(UserInfoContext);

  const onSubmit = (loginData: any) => {
    if (loginData.username && loginData.password) {
      login({ ...loginData, setUsername, setToken });
      setError(false);
      onClose();
    } else setError(true);
  };

  const handleClose = useCallback(() => {
    setError(false);
    onClose();
  }, []);

  return (
    <Modal blur open={open} onClose={handleClose} aria-labelledby="modal-login">
      <Modal.Header>
        <Text size={18}>
          Connect
          <Text size={18} b>
            {' '}
            JHipster Lite
          </Text>
        </Text>
      </Modal.Header>
      <Modal.Body>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Input
            clearable
            bordered
            fullWidth
            aria-label="modal-login-username"
            data-testid="modal-login-username"
            color="primary"
            size="lg"
            placeholder="Nom d'utilisateur"
            {...register('username')}
          />
          <Spacer y={0.3} />
          <Input.Password
            clearable
            bordered
            fullWidth
            aria-label="modal-login-password"
            data-testid="modal-login-password"
            color="primary"
            size="lg"
            placeholder="Mot de passe"
            {...register('password')}
          />
          <Spacer y={0.3} />
          {error && (
            <Text data-testid="error-message" size={13} color="error">
              Please complete fields above
            </Text>
          )}
          <Spacer y={1} />
          <input type="checkbox" {...register('rememberMe')} />
          <label>Remember me</label>
          <Spacer y={1} />
          <Button data-testid="submit-button" className="submit-button" type="submit" shadow auto>
            Log in
          </Button>
        </form>
      </Modal.Body>
    </Modal>
  );
};

export default LoginModal;
