/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IEmployee, Employee, MaritalStatus, Gender, Religion, EmployeeStatus, EmploymentType } from 'app/shared/model/employee.model';

describe('Service Tests', () => {
    describe('Employee Service', () => {
        let injector: TestBed;
        let service: EmployeeService;
        let httpMock: HttpTestingController;
        let elemDefault: IEmployee;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(EmployeeService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Employee(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                MaritalStatus.MARRIED,
                Gender.MALE,
                Religion.ISLAM,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                0,
                EmployeeStatus.IN_PROBATION,
                EmploymentType.PERMANENT,
                currentDate,
                'AAAAAAA',
                false,
                'image/png',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        birthDate: currentDate.format(DATE_FORMAT),
                        joiningDate: currentDate.format(DATE_FORMAT),
                        terminationDate: currentDate.format(DATE_FORMAT)
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

            it('should create a Employee', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        birthDate: currentDate.format(DATE_FORMAT),
                        joiningDate: currentDate.format(DATE_FORMAT),
                        terminationDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        birthDate: currentDate,
                        joiningDate: currentDate,
                        terminationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Employee(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Employee', async () => {
                const returnedFromService = Object.assign(
                    {
                        employeeId: 'BBBBBB',
                        fullName: 'BBBBBB',
                        fathersName: 'BBBBBB',
                        mothersName: 'BBBBBB',
                        birthDate: currentDate.format(DATE_FORMAT),
                        maritalStatus: 'BBBBBB',
                        gender: 'BBBBBB',
                        religion: 'BBBBBB',
                        permanentAddress: 'BBBBBB',
                        presentAddress: 'BBBBBB',
                        nId: 'BBBBBB',
                        tin: 'BBBBBB',
                        contactNumber: 'BBBBBB',
                        email: 'BBBBBB',
                        bloodGroup: 'BBBBBB',
                        emergencyContact: 'BBBBBB',
                        joiningDate: currentDate.format(DATE_FORMAT),
                        manager: 1,
                        employeeStatus: 'BBBBBB',
                        employmentType: 'BBBBBB',
                        terminationDate: currentDate.format(DATE_FORMAT),
                        reasonOfTermination: 'BBBBBB',
                        userAccount: true,
                        photo: 'BBBBBB',
                        hourlySalary: 1,
                        bankAccountNo: 'BBBBBB',
                        bankName: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        birthDate: currentDate,
                        joiningDate: currentDate,
                        terminationDate: currentDate
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

            it('should return a list of Employee', async () => {
                const returnedFromService = Object.assign(
                    {
                        employeeId: 'BBBBBB',
                        fullName: 'BBBBBB',
                        fathersName: 'BBBBBB',
                        mothersName: 'BBBBBB',
                        birthDate: currentDate.format(DATE_FORMAT),
                        maritalStatus: 'BBBBBB',
                        gender: 'BBBBBB',
                        religion: 'BBBBBB',
                        permanentAddress: 'BBBBBB',
                        presentAddress: 'BBBBBB',
                        nId: 'BBBBBB',
                        tin: 'BBBBBB',
                        contactNumber: 'BBBBBB',
                        email: 'BBBBBB',
                        bloodGroup: 'BBBBBB',
                        emergencyContact: 'BBBBBB',
                        joiningDate: currentDate.format(DATE_FORMAT),
                        manager: 1,
                        employeeStatus: 'BBBBBB',
                        employmentType: 'BBBBBB',
                        terminationDate: currentDate.format(DATE_FORMAT),
                        reasonOfTermination: 'BBBBBB',
                        userAccount: true,
                        photo: 'BBBBBB',
                        hourlySalary: 1,
                        bankAccountNo: 'BBBBBB',
                        bankName: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        birthDate: currentDate,
                        joiningDate: currentDate,
                        terminationDate: currentDate
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

            it('should delete a Employee', async () => {
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
