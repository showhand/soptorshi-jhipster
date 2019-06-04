/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { AllowanceManagementDetailComponent } from 'app/entities/allowance-management/allowance-management-detail.component';
import { AllowanceManagement } from 'app/shared/model/allowance-management.model';

describe('Component Tests', () => {
    describe('AllowanceManagement Management Detail Component', () => {
        let comp: AllowanceManagementDetailComponent;
        let fixture: ComponentFixture<AllowanceManagementDetailComponent>;
        const route = ({ data: of({ allowanceManagement: new AllowanceManagement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AllowanceManagementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AllowanceManagementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AllowanceManagementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.allowanceManagement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
