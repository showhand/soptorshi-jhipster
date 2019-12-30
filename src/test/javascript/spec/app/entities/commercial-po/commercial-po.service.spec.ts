/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommercialPoService } from 'app/entities/commercial-po/commercial-po.service';
import { CommercialPo, CommercialPoStatus, ICommercialPo } from 'app/shared/model/commercial-po.model';

describe('Service Tests', () => {
    describe('CommercialPo Service', () => {
        let injector: TestBed;
        let service: CommercialPoService;
        let httpMock: HttpTestingController;
        let elemDefault: ICommercialPo;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CommercialPoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CommercialPo(
                0,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                CommercialPoStatus.PO_CREATED,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        purchaseOrderDate: currentDate.format(DATE_FORMAT),
                        shipmentDate: currentDate.format(DATE_FORMAT),
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a CommercialPo', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        purchaseOrderDate: currentDate.format(DATE_FORMAT),
                        shipmentDate: currentDate.format(DATE_FORMAT),
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        purchaseOrderDate: currentDate,
                        shipmentDate: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CommercialPo(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CommercialPo', async () => {
                const returnedFromService = Object.assign(
                    {
                        purchaseOrderNo: 'BBBBBB',
                        purchaseOrderDate: currentDate.format(DATE_FORMAT),
                        originOfGoods: 'BBBBBB',
                        finalDestination: 'BBBBBB',
                        shipmentDate: currentDate.format(DATE_FORMAT),
                        poStatus: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        purchaseOrderDate: currentDate,
                        shipmentDate: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate
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

            it('should return a list of CommercialPo', async () => {
                const returnedFromService = Object.assign(
                    {
                        purchaseOrderNo: 'BBBBBB',
                        purchaseOrderDate: currentDate.format(DATE_FORMAT),
                        originOfGoods: 'BBBBBB',
                        finalDestination: 'BBBBBB',
                        shipmentDate: currentDate.format(DATE_FORMAT),
                        poStatus: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        purchaseOrderDate: currentDate,
                        shipmentDate: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate
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

            it('should delete a CommercialPo', async () => {
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
