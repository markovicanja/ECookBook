import { TestBed } from '@angular/core/testing';

import { HttpClientTestingModule } from '@angular/common/http/testing';

import { ServiceService } from './service.service';
import { User } from './model/user.model';
import { of } from 'rxjs';

describe('ServiceService', () => {
  let service: ServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(ServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all users', () => {
    let response: User[] = [];
    service.getAllUsers().subscribe(res => {
      response = res;
    });
    expect(response).toEqual([]);
  })

});
