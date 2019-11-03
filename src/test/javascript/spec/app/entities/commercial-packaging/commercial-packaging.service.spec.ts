/* tslint:disable max-line-length */
import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CommercialPackagingService } from 'app/entities/commercial-packaging/commercial-packaging.service';
import { CommercialPackaging, ICommercialPackaging } from 'app/shared/model/commercial-packaging.model';

describe('Service Tests', () => {
    describe('CommercialPackaging Service', () => {
        let injector: TestBed;
        let service: CommercialPackagingService;
        let httpMock: HttpTestingController;
        let elemDefault: ICommercialPackaging;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CommercialPackagingService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CommercialPackaging(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        consignmentDate: currentDate.format(DATE_FORMAT),
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

            it('should create a CommercialPackaging', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        consignmentDate: currentDate.format(DATE_FORMAT),
                        createOn: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        consignmentDate: currentDate,
                        createOn: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CommercialPackaging(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CommercialPackaging', async () => {
                const returnedFromService = Object.assign(
                    {
                        consignmentNo: 'BBBBBB',
                        consignmentDate: currentDate.format(DATE_FORMAT),
                        brand: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        consignmentDate: currentDate,
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

            it('should return a list of CommercialPackaging', async () => {
                const returnedFromService = Object.assign(
                    {
                        consignmentNo: 'BBBBBB',
                        consignmentDate: currentDate.format(DATE_FORMAT),
                        brand: 'BBBBBB',
                        createdBy: 'BBBBBB',
                        createOn: currentDate.format(DATE_FORMAT),
                        updatedBy: 'BBBBBB',
                        updatedOn: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        consignmentDate: currentDate,
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

            it('should delete a CommercialPackaging', async () => {
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
