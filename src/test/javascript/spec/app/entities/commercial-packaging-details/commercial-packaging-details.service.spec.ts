/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CommercialPackagingDetailsService } from 'app/entities/commercial-packaging-details/commercial-packaging-details.service';
import { CommercialPackagingDetails, ICommercialPackagingDetails } from 'app/shared/model/commercial-packaging-details.model';

describe('Service Tests', () => {
    describe('CommercialPackagingDetails Service', () => {
        let injector: TestBed;
        let service: CommercialPackagingDetailsService;
        let httpMock: HttpTestingController;
        let elemDefault: ICommercialPackagingDetails;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CommercialPackagingDetailsService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CommercialPackagingDetails(
                0,
                currentDate,
                currentDate,
                'AAAAAAA',
                0,
                'AAAAAAA',
                0,
                0,
                0,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        proDate: currentDate.format(DATE_FORMAT),
                        expDate: currentDate.format(DATE_FORMAT),
                        createOn: currentDate.format(DATE_FORMAT)
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

            it('should create a CommercialPackagingDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        proDate: currentDate.format(DATE_FORMAT),
                        expDate: currentDate.format(DATE_FORMAT),
                        createOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        proDate: currentDate,
                        expDate: currentDate,
                        createOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CommercialPackagingDetails(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CommercialPackagingDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        proDate: currentDate.format(DATE_FORMAT),
                        expDate: currentDate.format(DATE_FORMAT),
                        shift1: 'BBBBBB',
                        shift1Total: 1,
                        shift2: 'BBBBBB',
                        shift2Total: 1,
                        dayTotal: 1,
                        total: 1,
                        createdBy: 'BBBBBB',
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        proDate: currentDate,
                        expDate: currentDate,
                        createOn: currentDate
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

            it('should return a list of CommercialPackagingDetails', async () => {
                const returnedFromService = Object.assign(
                    {
                        proDate: currentDate.format(DATE_FORMAT),
                        expDate: currentDate.format(DATE_FORMAT),
                        shift1: 'BBBBBB',
                        shift1Total: 1,
                        shift2: 'BBBBBB',
                        shift2Total: 1,
                        dayTotal: 1,
                        total: 1,
                        createdBy: 'BBBBBB',
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        proDate: currentDate,
                        expDate: currentDate,
                        createOn: currentDate
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

            it('should delete a CommercialPackagingDetails', async () => {
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
