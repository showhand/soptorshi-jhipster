/* tslint:disable max-line-length */
import {getTestBed, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {map, take} from 'rxjs/operators';
import * as moment from 'moment';
import {DATE_TIME_FORMAT} from 'app/shared/constants/input.constants';
import {StockOutItemService} from 'app/entities/stock-out-item/stock-out-item.service';
import {IStockOutItem, StockOutItem} from 'app/shared/model/stock-out-item.model';

describe('Service Tests', () => {
    describe('StockOutItem Service', () => {
        let injector: TestBed;
        let service: StockOutItemService;
        let httpMock: HttpTestingController;
        let elemDefault: IStockOutItem;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StockOutItemService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new StockOutItem(0, 'AAAAAAA', 0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        stockOutDate: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a StockOutItem', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        stockOutDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        stockOutDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new StockOutItem(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a StockOutItem', async () => {
                const returnedFromService = Object.assign(
                    {
                        containerTrackingId: 'BBBBBB',
                        quantity: 1,
                        stockOutBy: 'BBBBBB',
                        stockOutDate: currentDate.format(DATE_TIME_FORMAT),
                        receiverId: 'BBBBBB',
                        receivingPlace: 'BBBBBB',
                        remarks: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        stockOutDate: currentDate
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

            it('should return a list of StockOutItem', async () => {
                const returnedFromService = Object.assign(
                    {
                        containerTrackingId: 'BBBBBB',
                        quantity: 1,
                        stockOutBy: 'BBBBBB',
                        stockOutDate: currentDate.format(DATE_TIME_FORMAT),
                        receiverId: 'BBBBBB',
                        receivingPlace: 'BBBBBB',
                        remarks: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        stockOutDate: currentDate
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

            it('should delete a StockOutItem', async () => {
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
