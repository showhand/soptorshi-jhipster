/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { AdvanceManagementDetailComponent } from 'app/entities/advance-management/advance-management-detail.component';
import { AdvanceManagement } from 'app/shared/model/advance-management.model';

describe('Component Tests', () => {
    describe('AdvanceManagement Management Detail Component', () => {
        let comp: AdvanceManagementDetailComponent;
        let fixture: ComponentFixture<AdvanceManagementDetailComponent>;
        const route = ({ data: of({ advanceManagement: new AdvanceManagement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AdvanceManagementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdvanceManagementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdvanceManagementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.advanceManagement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
