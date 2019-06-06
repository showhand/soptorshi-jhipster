/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { FineAdvanceLoanManagementDetailComponent } from 'app/entities/fine-advance-loan-management/fine-advance-loan-management-detail.component';
import { FineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';

describe('Component Tests', () => {
    describe('FineAdvanceLoanManagement Management Detail Component', () => {
        let comp: FineAdvanceLoanManagementDetailComponent;
        let fixture: ComponentFixture<FineAdvanceLoanManagementDetailComponent>;
        const route = ({ data: of({ fineAdvanceLoanManagement: new FineAdvanceLoanManagement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FineAdvanceLoanManagementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FineAdvanceLoanManagementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FineAdvanceLoanManagementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.fineAdvanceLoanManagement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
