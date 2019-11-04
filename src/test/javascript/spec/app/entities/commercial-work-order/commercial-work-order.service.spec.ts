/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CommercialWorkOrderService } from 'app/entities/commercial-work-order/commercial-work-order.service';
import { CommercialWorkOrder, ICommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';

describe('Service Tests', () => {
    describe('CommercialWorkOrder Service', () => {
        let injector: TestBed;
        let service: CommercialWorkOrderService;
        let httpMock: HttpTestingController;
        let elemDefault: ICommercialWorkOrder;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CommercialWorkOrderService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CommercialWorkOrder(
                0,
                'AAAAAAA',
                currentDate,
                currentDate,
                'AAAAAAA',
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
                        workOrderDate: currentDate.format(DATE_FORMAT),
                        deliveryDate: currentDate.format(DATE_FORMAT),
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedOn: currentDate.format(DATE_FORMAT)
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

            it('should create a CommercialWorkOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        workOrderDate: currentDate.format(DATE_FORMAT),
                        deliveryDate: currentDate.format(DATE_FORMAT),
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        workOrderDate: currentDate,
                        deliveryDate: currentDate,
                        createOn: currentDate,
                        updatedOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CommercialWorkOrder(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CommercialWorkOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        refNo: 'BBBBBB',
                        workOrderDate: currentDate.format(DATE_FORMAT),
                        deliveryDate: currentDate.format(DATE_FORMAT),
                        remarks: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        workOrderDate: currentDate,
                        deliveryDate: currentDate,
                        createOn: currentDate,
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

            it('should return a list of CommercialWorkOrder', async () => {
                const returnedFromService = Object.assign(
                    {
                        refNo: 'BBBBBB',
                        workOrderDate: currentDate.format(DATE_FORMAT),
                        deliveryDate: currentDate.format(DATE_FORMAT),
                        remarks: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        workOrderDate: currentDate,
                        deliveryDate: currentDate,
                        createOn: currentDate,
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

            it('should delete a CommercialWorkOrder', async () => {
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
