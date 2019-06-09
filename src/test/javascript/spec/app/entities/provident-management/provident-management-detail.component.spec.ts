/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ProvidentManagementDetailComponent } from 'app/entities/provident-management/provident-management-detail.component';
import { ProvidentManagement } from 'app/shared/model/provident-management.model';

describe('Component Tests', () => {
    describe('ProvidentManagement Management Detail Component', () => {
        let comp: ProvidentManagementDetailComponent;
        let fixture: ComponentFixture<ProvidentManagementDetailComponent>;
        const route = ({ data: of({ providentManagement: new ProvidentManagement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ProvidentManagementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProvidentManagementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProvidentManagementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.providentManagement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
