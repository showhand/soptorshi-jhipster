/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SystemAccountMapDetailComponent } from 'app/entities/system-account-map/system-account-map-detail.component';
import { SystemAccountMap } from 'app/shared/model/system-account-map.model';

describe('Component Tests', () => {
    describe('SystemAccountMap Management Detail Component', () => {
        let comp: SystemAccountMapDetailComponent;
        let fixture: ComponentFixture<SystemAccountMapDetailComponent>;
        const route = ({ data: of({ systemAccountMap: new SystemAccountMap(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SystemAccountMapDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SystemAccountMapDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SystemAccountMapDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.systemAccountMap).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
