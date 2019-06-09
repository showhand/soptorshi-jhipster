/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoptorshiTestModule } from '../../../test.module';
import { LoanManagementComponent } from 'app/entities/loan-management/loan-management.component';
import { LoanManagementService } from 'app/entities/loan-management/loan-management.service';
import { LoanManagement } from 'app/shared/model/loan-management.model';

describe('Component Tests', () => {
    describe('LoanManagement Management Component', () => {
        let comp: LoanManagementComponent;
        let fixture: ComponentFixture<LoanManagementComponent>;
        let service: LoanManagementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [LoanManagementComponent],
                providers: []
            })
                .overrideTemplate(LoanManagementComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LoanManagementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoanManagementService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new LoanManagement(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.loanManagements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
