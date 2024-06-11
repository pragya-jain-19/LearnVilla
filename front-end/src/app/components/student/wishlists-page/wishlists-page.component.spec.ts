import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WishlistsPageComponent } from './wishlists-page.component';

describe('WishlistsPageComponent', () => {
  let component: WishlistsPageComponent;
  let fixture: ComponentFixture<WishlistsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WishlistsPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WishlistsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
