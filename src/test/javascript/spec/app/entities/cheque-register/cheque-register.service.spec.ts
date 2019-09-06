/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ChequeRegisterService } from 'app/entities/cheque-register/cheque-register.service';
import { IChequeRegister, ChequeRegister } from 'app/shared/model/cheque-register.model';

describe('Service Tests', () => {
    describe('ChequeRegister Service', () => {
        let injector: TestBed;
        let service: ChequeRegisterService;
        let httpMock: HttpTestingController;
        let elemDefault: IChequeRegister;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ChequeRegisterService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ChequeRegister(0, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        chequeDate: currentDate.format(DATE_FORMAT),
                        realizationDate: currentDate.format(DATE_FORMAT),
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a ChequeRegister', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        chequeDate: currentDate.format(DATE_FORMAT),
                        realizationDate: currentDate.format(DATE_FORMAT),
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        chequeDate: currentDate,
                        realizationDate: currentDate,
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new ChequeRegister(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ChequeRegister', async () => {
                const returnedFromService = Object.assign(
                    {
                        chequeNo: 'BBBBBB',
                        chequeDate: currentDate.format(DATE_FORMAT),
                        status: 'BBBBBB',
                        realizationDate: currentDate.format(DATE_FORMAT),
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        chequeDate: currentDate,
                        realizationDate: currentDate,
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of ChequeRegister', async () => {
                const returnedFromService = Object.assign(
                    {
                        chequeNo: 'BBBBBB',
                        chequeDate: currentDate.format(DATE_FORMAT),
                        status: 'BBBBBB',
                        realizationDate: currentDate.format(DATE_FORMAT),
                        modifiedBy: 'BBBBBB',
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        chequeDate: currentDate,
                        realizationDate: currentDate,
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a ChequeRegister', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
