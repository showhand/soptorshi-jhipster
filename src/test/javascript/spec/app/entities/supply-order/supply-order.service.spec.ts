/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SupplyOrderService } from 'app/entities/supply-order/supply-order.service';
import { ISupplyOrder, SupplyOrder, SupplyOrderStatus } from 'app/shared/model/supply-order.model';

describe('Service Tests', () => {
    describe('SupplyOrder Service', () => {
        let injector: TestBed;
        let service: SupplyOrderService;
        let httpMock: HttpTestingController;
        let elemDefault: ISupplyOrder;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(SupplyOrderService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new SupplyOrder(
                0,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                currentDate,
                currentDate,
                SupplyOrderStatus.ORDER_RECEIVED,
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateOfOrder: currentDate.format(DATE_FORMAT),
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT),
                        deliveryDate: currentDate.format(DATE_FORMAT)
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

            it('should create a SupplyOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateOfOrder: currentDate.format(DATE_FORMAT),
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedOn: currentDate.format(DATE_TIME_FORMAT),
                        deliveryDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateOfOrder: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate,
                        deliveryDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new SupplyOrder(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a SupplyOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        orderNo: 'BBBBBB',
                        dateOfOrder: currentDate.format(DATE_FORMAT),
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT),
                        deliveryDate: currentDate.format(DATE_FORMAT),
                        status: 'BBBBBB',
                        areaWiseAccumulationRefNo: 'BBBBBB',
                        remarks: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateOfOrder: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate,
                        deliveryDate: currentDate
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

            it('should return a list of SupplyOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        orderNo: 'BBBBBB',
                        dateOfOrder: currentDate.format(DATE_FORMAT),
                        createdBy: 'BBBBBB',
                        createdOn: currentDate.format(DATE_TIME_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_TIME_FORMAT),
                        deliveryDate: currentDate.format(DATE_FORMAT),
                        status: 'BBBBBB',
                        areaWiseAccumulationRefNo: 'BBBBBB',
                        remarks: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateOfOrder: currentDate,
                        createdOn: currentDate,
                        updatedOn: currentDate,
                        deliveryDate: currentDate
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

            it('should delete a SupplyOrder', async () => {
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
