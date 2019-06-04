/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { LoanManagementDetailComponent } from 'app/entities/loan-management/loan-management-detail.component';
import { LoanManagement } from 'app/shared/model/loan-management.model';

describe('Component Tests', () => {
    describe('LoanManagement Management Detail Component', () => {
        let comp: LoanManagementDetailComponent;
        let fixture: ComponentFixture<LoanManagementDetailComponent>;
        const route = ({ data: of({ loanManagement: new LoanManagement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [LoanManagementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LoanManagementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LoanManagementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.loanManagement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
