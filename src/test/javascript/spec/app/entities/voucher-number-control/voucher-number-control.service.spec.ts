/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { VoucherNumberControlService } from 'app/entities/voucher-number-control/voucher-number-control.service';
import { IVoucherNumberControl, VoucherNumberControl, VoucherResetBasis } from 'app/shared/model/voucher-number-control.model';

describe('Service Tests', () => {
    describe('VoucherNumberControl Service', () => {
        let injector: TestBed;
        let service: VoucherNumberControlService;
        let httpMock: HttpTestingController;
        let elemDefault: IVoucherNumberControl;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(VoucherNumberControlService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new VoucherNumberControl(0, VoucherResetBasis.YEARLY, 0, 0, currentDate, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
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

            it('should create a VoucherNumberControl', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        modifiedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        modifiedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new VoucherNumberControl(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a VoucherNumberControl', async () => {
                const returnedFromService = Object.assign(
                    {
                        resetBasis: 'BBBBBB',
                        startVoucherNo: 1,
                        voucherLimit: 1,
                        modifiedOn: currentDate.format(DATE_FORMAT),
                        modifiedBy: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
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

            it('should return a list of VoucherNumberControl', async () => {
                const returnedFromService = Object.assign(
                    {
                        resetBasis: 'BBBBBB',
                        startVoucherNo: 1,
                        voucherLimit: 1,
                        modifiedOn: currentDate.format(DATE_FORMAT),
                        modifiedBy: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
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

            it('should delete a VoucherNumberControl', async () => {
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
