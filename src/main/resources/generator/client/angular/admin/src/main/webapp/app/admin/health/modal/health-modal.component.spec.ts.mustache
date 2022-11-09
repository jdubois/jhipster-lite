import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';

import { HealthModalComponent } from './health-modal.component';

class MatDialogMock {
  open() {
    return {
      afterClosed: () => of(true)
    }
  }
}

describe('HealthModalComponent', () => {
  let comp: HealthModalComponent;
  let fixture: ComponentFixture<HealthModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      providers: [
        {provide: MAT_DIALOG_DATA, useValue: MatDialogMock}
      ]
    })
      .overrideTemplate(HealthModalComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthModalComponent);
    comp = fixture.componentInstance;
  });

  describe('readableValue', () => {
    it('should return stringify value', () => {
      // GIVEN
      comp.healthDetails = {
        key: 'hystrix',
        status: 'UP',
      };

      // WHEN
      const result = comp.readableValue({ name: 'jhipster' });

      // THEN
      expect(result).toEqual('{"name":"jhipster"}');
    });

    it('should return string value', () => {
      // GIVEN
      comp.healthDetails = {
        key: 'hystrix',
        status: 'UP',
      };

      // WHEN
      const result = comp.readableValue('jhipster');

      // THEN
      expect(result).toEqual('jhipster');
    });

    it('should return storage space in an human readable unit (GB)', () => {
      // GIVEN
      comp.healthDetails = {
        key: 'diskSpace',
        status: 'UP',
      };

      // WHEN
      const result = comp.readableValue(1073741825);

      // THEN
      expect(result).toEqual('1.00 GB');
    });

    it('should return storage space in an human readable unit (MB)', () => {
      // GIVEN
      comp.healthDetails = {
        key: 'diskSpace',
        status: 'UP',
      };

      // WHEN
      const result = comp.readableValue(1073741824);

      // THEN
      expect(result).toEqual('1024.00 MB');
    });

    it('should return string value', () => {
      // GIVEN
      comp.healthDetails = {
        key: 'mail',
        status: 'UP',
      };

      // WHEN
      const result = comp.readableValue(1234);

      // THEN
      expect(result).toEqual('1234');
    });
  });
});
